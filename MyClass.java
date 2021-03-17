import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class MyClass {
    public static void main(String[] args) throws IOException {
        Scanner depar = new Scanner(System.in);
        System.out.println("Digite departamento a consultar");
        String Dep = depar.next();
        Scanner muni = new Scanner(System.in);
        System.out.println("Digite municipio a consultar");
        String Mun = muni.next();
        String Parte1 = "https://geoportal.dane.gov.co/laboratorio/serviciosjson/mgn/divipola.php?";
        //URL url = new URL("https://geoportal.dane.gov.co/laboratorio/serviciosjson/mgn/divipola.php?");

        
        //String Dep = "68";
        //String Mun = "001";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("codigo_departamento", Dep);
        parameters.put("codigo_municipio", Mun);
        parameters.put("clase","2");
        String parte2 = Parte1 + ParameterStringBuilder(parameters);
        URL url = new URL(parte2);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder(parameters));
        //System.out.println();
        out.flush();
        out.close();


        //ParameterStringBuilder(parameters);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            //System.out.println(inputLine);
            content.append(inputLine);
        }
        in.close();
        System.out.println(content);
         /*
        String [] Resul = content.toString().split("\"CPOB_CCDGO\":\"");
        String [] Resul2 = content.toString().split("\"CPOB_CNMBR\":\"");

        for (String a : Resul)
            
            System.out.println(a);
          */
        //con.disconnect();

        //System.out.println(con.getHeaderField(String "resultado"));


    }

    private static String ParameterStringBuilder(Map<String, String> parametro) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : parametro.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
            //System.out.println("hecho");
        }

        String resultString = result.toString();
        //System.out.println(resultString.substring(0, resultString.length() - 1));
        return resultString.substring(0, resultString.length() - 1);
    }
}
