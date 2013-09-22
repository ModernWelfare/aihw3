package util;

/*******************************************************************************
 * This files was developed for CS4341: Artificial Intelligence.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
	private static FileReader filereaderInstance = null;
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static FileReader getInstance() {
		if (filereaderInstance == null) {
			filereaderInstance = new FileReader();
		}
		return filereaderInstance;
	}

	public List<String> readCSVFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		return Files.readAllLines(path, ENCODING);
	}
}
