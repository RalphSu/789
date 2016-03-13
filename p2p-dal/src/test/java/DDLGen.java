import java.io.File;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.google.common.io.Files;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class DDLGen {

    @Test
    public void test() throws Exception {
        Collection<File> maps = FileUtils.listFiles(new File("./src/main/resources/sqlmap"), new String[] { "xml" },
                false);
        System.out.println(maps.toString());

        for (File f : maps) {
            SAXReader reader = new SAXReader(false);
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            Document d = reader.read(f);
            Element root = d.getRootElement();
            if (!root.getName().equalsIgnoreCase("sqlmap")) {
                System.out.println(root.getName());
                continue;
            }
            
            Element resultEle = root.element("resultMap");
            String className = resultEle.attributeValue("class");
            System.out.print("class name: " + className);
            String tableName = generateTableName(className);
            System.out.print("table name: " + tableName); System.out.println();
            
            StringBuilder sb = new StringBuilder(String.format("use yr789; \nDROP TABLE IF EXISTS %s; \nCREATE TABLE %s (", tableName, tableName));
            
            List<Element> columMaps = resultEle.elements();
            for (Element column :columMaps) {
                String colName = column.attributeValue("column");
                String jdbcType = column.attributeValue("jdbcType");
                if (jdbcType.equalsIgnoreCase("varchar")) {
                    jdbcType = jdbcType+"(400)"; // default as varchar 400
                }
                else if (jdbcType.equalsIgnoreCase("enum")) {
                    jdbcType = "VARCHAR(400)";
                } else if (jdbcType.equalsIgnoreCase("timestamp")) {
                    jdbcType = jdbcType + " DEFAULT CURRENT_TIMESTAMP ";
                    if (colName.toLowerCase().contains("update")  || colName.toLowerCase().contains("modify")) {
                        jdbcType = jdbcType + " ON UPDATE CURRENT_TIMESTAMP "; 
                    }
                } else if (jdbcType.equalsIgnoreCase("number")) {
                    jdbcType = "DECIMAL(11,8)";
                }
                
                
//                System.out.println(colName + " - " + jdbcType);
                
                sb.append("\n\t").append(colName).append(' ').append(jdbcType).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            
            sb.append("\n").append(");");
            
            System.out.println(sb.toString());
            Files.write(sb.toString(), new File("./src/main/resources/ddls/" + tableName + ".sql"), Charset.defaultCharset());
            
            // for test
//            break;
        }
    }

    private String generateTableName(String className) {
        String tableName = className.substring(className.lastIndexOf('.') + 1);
        // remove do
        if (tableName.endsWith("DO")) {
            tableName = tableName.substring(0, tableName.length() - 2);
        }
        
        StringBuilder sb = new StringBuilder();
        boolean meetUpper = false;
        for (int i = 0; i < tableName.length(); i++) {
            char c = tableName.charAt(i);
            if (Character.isUpperCase(c)) {
                if (!meetUpper) {
                    sb.append(Character.toLowerCase(c));
                    meetUpper = true;
                } else {
                    sb.append("_").append(Character.toLowerCase(c));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
