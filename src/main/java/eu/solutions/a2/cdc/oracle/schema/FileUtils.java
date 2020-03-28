/**
 * Copyright (c) 2018-present, A2 Rešitve d.o.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package eu.solutions.a2.cdc.oracle.schema;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import eu.solutions.a2.cdc.oracle.OraTable4LogMiner;

/**
 * 
 * @author averemee
 *
 */
public class FileUtils {

	public static Map<Long, OraTable4LogMiner> readDictionaryFile(final String fileName) throws IOException {
		Map<String, Map<String, Object>> fileData = new HashMap<>();
		final ObjectReader reader = new ObjectMapper()
				.readerFor(fileData.getClass());
		InputStream is = new FileInputStream(fileName);
		fileData = reader.readValue(is);
		is.close();
		final Map<Long, OraTable4LogMiner> schemas = new ConcurrentHashMap<>();
		fileData.forEach((k, v) -> {
			schemas.put(Long.parseLong(k), new OraTable4LogMiner(v));
		});
		fileData = null;
		return schemas;
	}

}
