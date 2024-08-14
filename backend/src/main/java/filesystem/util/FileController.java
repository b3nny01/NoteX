package filesystem.util;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class FileController {

	private TikaConfig tikaConfig;
	private final String RELATIVE_SHADOWED_PATH = "notes_shadowed";
	private final String RELATIVE_READABLE_PATH = "notes_readable";
	private List<String> supportedTypes;

	public FileController() throws TikaException, IOException {
		super();
		this.tikaConfig = new TikaConfig();
		this.supportedTypes = List.of("application/pdf");
	}

	public synchronized String persist(ServletContext context, Part part) throws IllegalFileTypeException {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (part == null)
			throw new NullPointerException("part is null");

		// declaration and initialization
		String relativeFilePath = "";
		String realFilePath = "";
		Metadata metadata = new Metadata();
		String mimeType = "";
		File file = null;

		// file type analysis and file creation on the server
		try {
			mimeType = tikaConfig.getDetector().detect(part.getInputStream(), metadata).toString();
			if (supportedTypes.contains(mimeType)) {
				do {
					relativeFilePath = RELATIVE_READABLE_PATH + File.separator + UUID.randomUUID().toString();
					file = new File(context.getRealPath(relativeFilePath));
				} while (file.exists());
				realFilePath = context.getRealPath(relativeFilePath);
				part.write(realFilePath);
			} else {
				throw new IllegalFileTypeException();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		// return
		return relativeFilePath;
	}

	public synchronized void replace(ServletContext context, String relativeFilePath, Part part)
			throws IOException, IllegalFileTypeException {
		// parameters check
		if (context == null)
			throw new NullPointerException("context is null");
		if (relativeFilePath == null)
			throw new NullPointerException("relativeFilePath is null");
		if (part == null)
			throw new NullPointerException("part is null");

		// declaration and initialization
		String realFilePath = "";
		Metadata metadata = new Metadata();
		String mimeType = "";

		// file type analysis and file creation on the server
		mimeType = tikaConfig.getDetector().detect(part.getInputStream(), metadata).toString();
		if (supportedTypes.contains(mimeType)) {
			realFilePath = context.getRealPath(relativeFilePath);
			part.write(realFilePath);
		} else {
			throw new IllegalFileTypeException();
		}

	}

	public synchronized void delete(ServletContext context, String relativeFilePath) throws IOException {
		// parameters check
		if (context == null)
			throw new NullPointerException("context is null");
		if (relativeFilePath == null)
			throw new NullPointerException("relativeFilePath is null");

		// declaration and initialization
		File file = new File(context.getRealPath(relativeFilePath));

		if (file.exists()) {
			file.delete();
		} else {
			throw new IOException("file " + context.getRealPath(relativeFilePath) + " does not exist");
		}

	}

	public synchronized String shadow(ServletContext context, String relativeFilePath) {
		// parameter check
		if (context == null)
			throw new NullPointerException("contex is null");
		if (relativeFilePath == null)
			throw new NullPointerException("relativeFilePath is null");

		// declaration and initialization
		String relativeShadowedFilePath = "";
		File file = new File(context.getRealPath(relativeFilePath));
		File removedFile = null;

		// removing the file
		if (file.exists()) {
			do {
				relativeShadowedFilePath = RELATIVE_SHADOWED_PATH + File.separator + UUID.randomUUID();
				removedFile = new File(relativeShadowedFilePath);
			} while (removedFile.exists());
			file.renameTo(removedFile);
		} else {
			throw new IllegalArgumentException("relativeFilePath is not valid");
		}

		// return
		return relativeShadowedFilePath;
	}

	public synchronized String unshadow(ServletContext context, String relativeFilePath) {
		// parameter check
		if (context == null)
			throw new NullPointerException("contex is null");
		if (relativeFilePath == null)
			throw new NullPointerException("relativeFilePath is null");

		// declaration and initialization
		String relativeRestoredFilePath = "";
		File file = new File(context.getRealPath(relativeFilePath));
		File removedFile = null;

		// restoring the file
		if (file.exists()) {
			do {
				relativeRestoredFilePath = RELATIVE_READABLE_PATH + File.separator + UUID.randomUUID();
				removedFile = new File(relativeRestoredFilePath);
			} while (removedFile.exists());
			file.renameTo(removedFile);
		} else {
			throw new IllegalArgumentException("relativeFilePath is not valid");
		}

		// return
		return relativeRestoredFilePath;
	}

}