package filesystem.util;

import java.io.IOException;

import org.apache.tika.exception.TikaException;

public class FileSystemUtil {
	private static FileController controllerFile=initFileController();
	
	private static FileController initFileController()
	{
		try {
			return new FileController();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		} 
	}
	
	public static FileController getFileController()
	{
		return controllerFile;
	}
}
