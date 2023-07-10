package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import models.Sale;

@ManagedBean
@SessionScoped
public class SalesBean {

	private List<Sale> sales = new ArrayList<>();

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
	
	public void fetchSales() {
        String url = "http://localhost:8080/backend/api/sales";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            
            String jwtToken = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("jwtToken");
            httpGet.setHeader("Authorization", "Bearer " + jwtToken);
            
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String responseBody = EntityUtils.toString(responseEntity);
                FacesContext context = FacesContext.getCurrentInstance();

                if (response.getStatusLine().getStatusCode() == 200) {
                	ObjectMapper objectMapper = new ObjectMapper();
                	JsonNode responseJson = objectMapper.readTree(responseBody);
                	
                    ArrayNode itemsNode = (ArrayNode) responseJson.get("items");
                    List<Sale> responseSales = new ArrayList<>();

                    for (JsonNode itemNode : itemsNode) {
                        Sale sale = objectMapper.treeToValue(itemNode, Sale.class);
                        responseSales.add(sale);
                    }

                    setSales(responseSales);
                	
                } else {
                    try {
                        context.getExternalContext().redirect("http://localhost:8080/frontend/login.xhtml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void deleteSale(Sale sale) {
		
	}
}
