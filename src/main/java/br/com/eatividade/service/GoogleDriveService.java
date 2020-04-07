package br.com.eatividade.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import br.com.eatividade.model.Atividade;
import br.com.eatividade.util.TokenUtil;

@Service
public class GoogleDriveService {

	@Autowired private StorageService storageService;
//	@Autowired private TokenCliente tokenCliente;
	
	private static final String TOKEN_REFRESH= "1//04yM8Bul27_7GCgYIARAAGAQSNwF-L9Irz35_7KMPO3kstPKT7ihGWMjrmuVThoeXWqnXrhWYWcDCCRC8OFC3QJ7g9bBC8H0Tudg";
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String APPLICATION_NAME = "E-Atividade";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/keys-gdrive/credentials.json";
    private static GoogleAuthorizationCodeFlow flow;
    
    
	@PostConstruct
	public void init() throws Exception{
		// Load client secrets.
        InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, TokenUtil.CLIENT_ID, TokenUtil.SECRET_KEY, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH))).setAccessType("offline")
				.build();
		
		
	}
    
    private Credential getCredentials() throws IOException {
    	
    	LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    	
//    	Token token = tokenCliente.findByRefreshToken();
    	
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("EAtividadeStore");
    }
    
    
//    private static Credential getCredentials() throws IOException {
//        
//    	Credential credential = flow.loadCredential("EAtividadeStore");
//    	if(credential != null){
//    		boolean tokenValid = credential.refreshToken();
//    	}
//    	
//    	LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("EAtividadeStore");
//    }
    
    
//	public boolean salvaPdfNoDrive(Atividade retorno, MultipartFile pdf) {
//		
//		try{
//			
//			// Build a new authorized API client service.
//			Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
//					.setApplicationName(APPLICATION_NAME).build();
//			
//			String idPasta = getIdPasta(retorno.getTurma().getNome(), drive);
//			
//			File fileMetadata = new File();
//			fileMetadata.setName(retorno.getId().toString());
//			fileMetadata.setParents(Collections.singletonList(idPasta));
//			
//			FileContent mediaContent = new FileContent("application/pdf", 
//					storageService.salvarPdfTemporario(pdf));
//			
//			File uploaded = drive.files().create(fileMetadata, mediaContent).setFields("id").execute();
//			
//			return uploaded != null && !uploaded.getId().isEmpty();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return false;
//
//	}

	private String getIdPasta(String nomeTurma, Drive drive) throws IOException {
		
		String idPasta = consultaPasta(nomeTurma, drive);
		
		return (idPasta != null && !idPasta.isEmpty()) ? idPasta : criarPasta(nomeTurma, drive);
	}

	private String consultaPasta(String nomeTurma, Drive drive) throws IOException {
		String id = "";
		
		String pageToken = null;
		do {
		  FileList result = drive.files().list()
		      .setQ("name='".concat(nomeTurma).concat("'"))
		      .setFields("nextPageToken, files(id, name)")
		      .setPageToken(pageToken)
		      .execute();
		  
		  for (File file : result.getFiles()) {
			  	 id = file.getId();
			  	 break;
		  }
		  
		  pageToken = result.getNextPageToken();
		} while (pageToken != null);
		
		return id;
	}

	private String criarPasta(String nome, Drive drive) throws IOException {
		File fileMetadata = new File();
		fileMetadata.setName(nome);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		File file = drive.files().create(fileMetadata)
		    .setFields("id")
		    .execute();
		
		return file.getId();
	}

}
