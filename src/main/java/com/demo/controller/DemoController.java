package com.demo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.config.ConfigProperty;
import com.demo.crud.service.FileUploadService;
import com.demo.crud.service.IPersonaService;

import io.minio.BucketExistsArgs;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;

@RestController
@RequestMapping("/")
public class DemoController {

	private static Logger LOG = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private IPersonaService srv;

	@Autowired
	private FileUploadService upf;

	@Autowired
	private ConfigProperty configs;

	@GetMapping("/")
	public String testing() {
		// service.registrar("foo");

		return "ONLINE";
	}

	@GetMapping("/api")
	public String api() {
		String ret = "Post /uploadfile/  -  key: file, type: body-file, value: file upload <br>";
		ret = ret+"Get /cigee-galery/ - key: image, value: name-image-view <br>";
		ret = ret+"Get /minio/info <br>";
		ret = ret+"Get /minio/createBucket - key: name, value name-bucket-create <br>";
		ret = ret+"Get /minio/list";
		ret = ret+"Post /minio/uploadFile - key: bucket, value name-bucket-exist && key: name, value: name-file && key: file, type: body-file, value: file-upload";
		ret = ret+"Get /minio/openFile - key: bucket, value: bucket-exist && key: name, value: name-to-preview";
		
		return ret;
	}


	@PostMapping("/uploadfile") 
	private String uploadFile(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		upf.uploadFile(file);
		return "Upload file " + file.getName();

	}

	@GetMapping("/minio/info")
	public String minioInfo() {
		return "upload: " + configs.getUploadroot() + "</br>" + "endpoint: " + configs.getEndpoint() + "</br>"
				+ "access-key: " + configs.getAccesskey() + "</br>" + "secret-key:" + configs.getSecretkey();
	}

	@GetMapping("/minio/createBucket")
	public String minioCreateBucket(@RequestParam("name") String bucketGalery) throws InvalidKeyException,
			ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException,
			NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {

		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();

		Boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketGalery).build());
		String mjs = "";
		if (!found) {
			mjs = "Creating Bucket";
			LOG.info(mjs);
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketGalery).build());
		} else {
			mjs = "Bucket already exists";
			LOG.info(mjs);
		}
		return mjs;
	}

	@GetMapping("/minio/list")
	public String minioListBuckets()
			throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException,
			InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();

		String mjs = null;
		List<Bucket> bucketList = minioClient.listBuckets();
		for (Bucket bucket : bucketList) {
			if (!bucketList.isEmpty()) {
				mjs = mjs + " " + (bucket.creationDate() + ", " + bucket.name());
			} else {
				mjs = "Is empty";
			}
		}

		return mjs;

	}

	@PostMapping("/minio/uploadFile")
	private Object minioUploadFile(@RequestParam("bucket") String bucket,@RequestParam("nombre") String name,@RequestParam("file") MultipartFile file) {
		
	//, @RequestParam("file") File file
		Map<String,String> aux = new HashMap<>();
//		
//		name = "sql";
//		ruta = "C:\\\\Users\\\\aspso\\\\Documents\\\\sql.txt";
//		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
//				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();
//
//		minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(name).filename(ruta).build());
//		minioClient.putObject(null);
//		minioClient.putObject(
//			    PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
//			            inputStream, -1, 10485760)
//			        .contentType("video/mp4")
//			        .build());
//		return "Upload file ";
		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();
		
		try {
			minioClient.putObject(
				    PutObjectArgs.builder().bucket(bucket).object(name).stream(
				    		file.getInputStream(), -1, 10485760).build());
			aux.put("message", "Upload File ");
			return aux;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;

	}
	
	@GetMapping("/minio/downloadFile")
	private String minioDownloadFile(@RequestParam("bucket") String bucket,@RequestParam("name") String name)	
			throws IllegalStateException, IOException, InvalidKeyException, ErrorResponseException,
			InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException,
			ServerException, XmlParserException, IllegalArgumentException {
		
		String file;
		name = "sql";
		file = "C:\\Users\\aspso\\Downloads\\sql.txt";
		
		
		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();

		minioClient.downloadObject(
				  DownloadObjectArgs.builder()
				  .bucket(bucket)
				  .object(name)
				  .filename(file)
				  .build());
		
		return "Download file, "+name;

	}

	@GetMapping("/minio/openFile")
	private void openFile(@RequestParam("bucket") String bucket, @RequestParam("name") String name,
			HttpServletResponse response)
			throws IllegalStateException, IOException, InvalidKeyException, ErrorResponseException,
			InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException,
			ServerException, XmlParserException, IllegalArgumentException {


		MinioClient minioClient = MinioClient.builder().endpoint(configs.getEndpoint())
				.credentials(configs.getAccesskey(), configs.getSecretkey()).build();
		
		InputStream stream = minioClient.getObject(
				  GetObjectArgs.builder()
				  .bucket(bucket)
				  .object(name)
				  .build());


		IOUtils.copy(stream, response.getOutputStream());
		stream.close();

	}
	
	

}
