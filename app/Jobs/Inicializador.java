package Jobs;

import Enums.StatusCaixa;
import models.Adiministrador;
import models.Caixa;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

    @Override
    public void doJob() throws Exception {

        if (Adiministrador.count() == 0) {
            Adiministrador adim = new Adiministrador();
            adim.email = "matheuszakkwylde360@gmail.com";
            adim.senha = "155376";
            adim.nome = "Adiministrador";
            adim.save();
        }

        if (Caixa.count() == 0) {
            Caixa caixa = new Caixa();
            caixa.status = StatusCaixa.INATIVO;
            caixa.total = 0;
            caixa.data = null;
            caixa.save();
        }
        
        
    }
}
