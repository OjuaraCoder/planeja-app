package com.ojuara.planeja.infra.sandbox;

import com.ojuara.planeja.dominio.cartao.enums.BandeiraCartaoEnum;
import com.ojuara.planeja.dominio.cartao.CartaoRepository;
import com.ojuara.planeja.dominio.cartao.model.CartaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Sandbox implements CommandLineRunner {

    @Autowired
    private CartaoRepository cartaoRepository;

    public void salvarCartao() {
         CartaoEntity cartao = new CartaoEntity();
         cartao.setNome("Meu Cartão");
         cartao.setBandeira(BandeiraCartaoEnum.VISA);

         cartaoRepository.save(cartao);
    }

    @Override
    public void run(String... args) throws Exception {
       // salvarCartao();
    }
}
