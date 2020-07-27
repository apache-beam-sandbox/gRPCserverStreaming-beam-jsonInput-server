package server;

import com.fileReader.generated.stubs.FileReaderRequest;
import com.fileReader.generated.stubs.FileReaderResponse;
import com.fileReader.generated.stubs.FileReaderServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderServiceImpl extends FileReaderServiceGrpc.FileReaderServiceImplBase {

    @Override
    public void readFile(FileReaderRequest request,
                         StreamObserver<FileReaderResponse> responseObserver) {
        //asyncUnimplementedUnaryCall(getReadFileMethod(), responseObserver);
        System.out.println("Request received");
        String path = request.getFilePath();
        System.out.println("requested file path:"+path);
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                //System.out.println(nextLine+"this is line");
                responseObserver.onNext(FileReaderResponse.newBuilder()
                        .setLine(nextLine)
                        .build());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File location : "+ path +" is invalid."+e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception occurred while reading the file"+e.getMessage());
        }
        finally {
           // localTmpFile.delete();
        }
        responseObserver.onCompleted();
        System.out.println("Response sent successfully");
    }
 }

