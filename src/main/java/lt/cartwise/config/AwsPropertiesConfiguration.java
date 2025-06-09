package lt.cartwise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:aws-credentials.properties")
public class AwsPropertiesConfiguration {

}
