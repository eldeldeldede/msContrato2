package cl.duoc.msContrato2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msContrato2.model.Contrato;
import cl.duoc.msContrato2.model.EstadoContrato;
import cl.duoc.msContrato2.repository.ContratoRepository;
import cl.duoc.msContrato2.repository.EstadoContratoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(ContratoRepository contratoRepo,
                                   EstadoContratoRepository estadoContratoRepo
    ){
                return args -> {
                    if(contratoRepo.count() > 0){
                        System.out.println("Base de datos ya inicializada");
                    }else{
                        EstadoContrato estado1 = new EstadoContrato(null, "Activo");
                        EstadoContrato estado2 = new EstadoContrato(null, "Finalizado");
                        EstadoContrato estado3 = new EstadoContrato(null, "Cancelado");
                        
                        Contrato contrato1 = new Contrato(null,"CONT-1001","1","2026-05-17","2026-05-25",150000,estado1);
                        Contrato contrato2 = new Contrato(null,"CONT-1002","2","2026-06-01","2026-06-10",200000,estado2);
                        Contrato contrato3 = new Contrato(null,"CONT-1003","3","2026-06-15","2026-06-20",90000,                        estado3);
                    
                        estadoContratoRepo.save(estado1);
                        estadoContratoRepo.save(estado2);
                        estadoContratoRepo.save(estado3);

                        contratoRepo.save(contrato1);
                        contratoRepo.save(contrato2);
                        contratoRepo.save(contrato3);

                        System.out.println("Datos cargados");


                }
            };
    }
}
