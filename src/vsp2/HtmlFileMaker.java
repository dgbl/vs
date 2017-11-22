/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vsp2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author DNS
 */
public class HtmlFileMaker {

    
    Date date = new Date();
    String html = "";
    String path = "C:\\Users\\DNS\\Documents\\NetBeansProjects\\VSP2\\index.html";

    public void makeFile(int a, int b, int c, int d, int e) throws IOException {

        html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "	<head>\n"
                + "		<meta charset='UTF-8'>\n"
                + "			<title>Kuehlschrank</title>\n"
                + "		</head>\n"
                + "		<body><h2>Inhalt: </h2>" + date + "\n"
                + "			<table border=1>\n"
                + "				<thead>\n"
                + "					<tr>\n"
                + "						<th>Bezeichnung</th>\n"
                + "						<th>Anzahl</th>\n"
                + "					</tr>\n"
                + "				</thead>\n"
                + "				<tbody>\n"
                + "					<tr>\n"
                + "						<td>Apfel</td>\n"
                + "						<th>" + a + "</th>\n"
                + "					</tr>\n"
                + "					<tr>\n"
                + "						<td>Banane</td>\n"
                + "						<th>" + b + "</th>\n"
                + "					</tr>\n"
                + "					<tr>\n"
                + "						<td>Birne</td>\n"
                + "						<th>" + c + "</th>\n"
                + "					</tr>\n"
                + "					<tr>\n"
                + "						<td>Mango</td>\n"
                + "						<th>" + d + "</th>\n"
                + "					</tr>\n"
                + "					<tr>\n"
                + "						<td>Durian</td>\n"
                + "						<th>" + e + "</th>\n"
                + "					</tr>\n"
                + "				</tbody>\n"
                + "			</table>\n"
                + "		</body>\n"
                + "	</html>";

        File f = new File(path);

        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(html);
        System.out.println("---HTML erstellt");
        bw.close();
    }
}
