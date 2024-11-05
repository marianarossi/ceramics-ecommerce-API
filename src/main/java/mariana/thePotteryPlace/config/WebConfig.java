package mariana.thePotteryPlace.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebConfig {
    //converte model para DTO e DTO para model
    @Bean //delega para o spring gerenciar a dependencia, para nao ter que instanciar toda vez
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
