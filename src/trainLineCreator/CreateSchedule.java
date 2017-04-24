package trainLineCreator;
//download Dropbox SDK and import jar to build path
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderContinueErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;

public class CreateSchedule {
	//access token for dropbox folder
    private static final String ACCESS_TOKEN = "vI9KfDN7ymAAAAAAAAAAkdY3E_YoSGHu2zlHPaFV1BfqmftMDGalz4jg6mfLOZFw";

    public CreateSchedule() throws DbxException, IOException{
    	DbxClientV2 client = createClient();
    }
    private static DbxClientV2 createClient() throws DbxException, IOException {
        // Create Dropbox client using access token
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial");
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
        return client;
    }

	public static void downloadFile(DbxClientV2 client, String filePath) {
		try
        {
            //output file for download
        	File file = new File("data/"+filePath);
            OutputStream downloadFile = new FileOutputStream(file);
            try
            {
            FileMetadata metadata = client.files().downloadBuilder(filePath)
                    .download(downloadFile);
            }
            finally
            {
                downloadFile.close();
            }
        }
        //exception handled
        catch (DbxException e)
        {
            //error downloading file
        	System.out.println("Unable to download file to local system\n Error: " + e);
        }
        catch (IOException e)
        {
            //error downloading file
            System.out.println("Unable to download file to local system\n Error: " + e);
        }
	}

	public static void uploadFile(DbxClientV2 client, String filePath)
			throws FileNotFoundException, UploadErrorException, DbxException, IOException {
		File file = new File("data/"+filePath);
        try (InputStream in =  new FileInputStream(file)){
            FileMetadata metadata = client.files().uploadBuilder(filePath)
            	.withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(in);
        }
	}

	public static void showFiles(DbxClientV2 client)
			throws ListFolderErrorException, DbxException, ListFolderContinueErrorException {
		// Get files and folder metadata from Dropbox root directory
        ListFolderResult result = client.files().listFolder("");
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }

            result = client.files().listFolderContinue(result.getCursor());
        }
	}
}