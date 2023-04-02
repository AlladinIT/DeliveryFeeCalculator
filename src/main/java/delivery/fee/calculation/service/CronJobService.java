package delivery.fee.calculation.service;

import delivery.fee.calculation.dto.WeatherData;
import delivery.fee.calculation.repository.WeatherDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

@Service
public class CronJobService {

    private WeatherDataRepository weatherDataRepository;

    public CronJobService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    @Scheduled(cron = "${cron.expression}")
    public void insertWeatherDataIntoDatabase() {
        try {
            long now = System.currentTimeMillis() / 1000;
            System.out.println(
                    "schedule tasks using cron jobs - " + now);

            String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().parse(new URL(url).openStream());
            document.getDocumentElement().normalize();

            String timestamp = document.getDocumentElement().getAttribute("timestamp");
            Date date = new Date(Long.parseLong(timestamp) * 1000);

            NodeList nodeList = document.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of all sub-elements.
                    String name = elem.getElementsByTagName("name")
                            .item(0).getTextContent();

                    if (Objects.equals(name, "Tallinn-Harku") ||
                            Objects.equals(name, "Tartu-Tõravere") ||
                            Objects.equals(name, "Pärnu")) {
                        String wmoCode = elem.getElementsByTagName("wmocode")
                                .item(0).getTextContent();
                        System.out.println(wmoCode);

                        String airTemperature = elem.getElementsByTagName("airtemperature")
                                .item(0).getTextContent();
                        System.out.println(airTemperature);

                        String windSpeed = elem.getElementsByTagName("windspeed")
                                .item(0).getTextContent();
                        System.out.println(windSpeed);

                        String phenomenon = elem.getElementsByTagName("phenomenon")
                                .item(0).getTextContent();
                        System.out.println(phenomenon);

                        WeatherData weatherData = new WeatherData(
                                name,
                                Integer.parseInt(wmoCode),
                                new BigDecimal(airTemperature),
                                new BigDecimal(windSpeed),
                                phenomenon,
                                date);
                        weatherDataRepository.save(weatherData);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cron job failed: " + e.getMessage());
        }

    }

}
