package beans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.RequestContent;
import org.apache.http.util.EntityUtils;
import org.jboss.weld.context.RequestContext;
import org.primefaces.PrimeFaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	private String email;
	private String password;
	private String errorMessage;
	private String token;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void login() {
        String url = "http://localhost:8080/backend/api/users/login";
        String redirectUrl = "http://localhost:8080/frontend/index.xhtml";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            String payload = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String responseBody = EntityUtils.toString(responseEntity);

                if (response.getStatusLine().getStatusCode() == 200) {
                    String token = parseTokenFromResponse(responseBody);

                    FacesContext context = FacesContext.getCurrentInstance();
                    
                    PrimeFaces.current().ajax().addCallbackParam("jwtToken", token);
                                        
                    try {
                        context.getExternalContext().redirect(redirectUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                	ObjectMapper objectMapper = new ObjectMapper();
                	JsonNode responseJson = objectMapper.readTree(responseBody);
                	
                	String erroMsg = responseJson.get("message").asText();
                	setErrorMessage(erroMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String parseTokenFromResponse(String responseBody) throws JsonMappingException, JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode responseJson = objectMapper.readTree(responseBody);
    	
    	return responseJson.get("token").asText();
    }

}
