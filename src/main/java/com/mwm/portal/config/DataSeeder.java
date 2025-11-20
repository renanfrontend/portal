package com.mwm.portal.config;

// IMPORTS IMPORTANTES: O "*" garante que ele ache Cooperado, Agenda, etc.
import com.mwm.portal.model.*;
import com.mwm.portal.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    // Injeção de todos os Repositórios necessários
    private final UserRepository userRepository;
    private final CooperadoRepository cooperadoRepository;
    private final PortariaRepository portariaRepository;
    private final AbastecimentoRepository abastecimentoRepository;
    private final ColetaRepository coletaRepository;
    private final QualidadeRepository qualidadeRepository;
    private final AgendaRepository agendaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem usuários. Se não, popula o banco.
        if (userRepository.count() == 0) {
            System.out.println("🌱 INICIANDO POPULAÇÃO DO BANCO DE DADOS...");

            try {
                seedUsers();          // Cria Admin, Editor, Leitor
                seedCooperadosReal(); // Dados do PDF Cooperados
                seedAgendaReal();     // Dados do PDF Planejamento
                seedPortariaMock();   // Dados do Mock Frontend
                seedAbastecimentosMock();
                seedColetasMock();
                seedQualidadeMock();
                
                System.out.println("✅ BANCO DE DADOS POPULADO COM SUCESSO!");
            } catch (Exception e) {
                System.err.println("❌ ERRO AO POPULAR BANCO: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ℹ️ O banco de dados já contém dados. O Seeder não foi executado.");
        }
    }

    // --- 1. USUÁRIOS ---
    private void seedUsers() {
        System.out.println("   -> Criando Usuários...");
        
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@mwm.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.administrador);
        admin.setFiliais(Arrays.asList("Toledo - PR", "Cascavel - PR"));
        userRepository.save(admin);

        User editor = new User();
        editor.setUsername("editor");
        editor.setEmail("editor@mwm.com");
        editor.setPassword(passwordEncoder.encode("editor123"));
        editor.setRole(User.Role.editor);
        editor.setFiliais(List.of("Toledo - PR"));
        userRepository.save(editor);

        User leitor = new User();
        leitor.setUsername("porteiro");
        leitor.setEmail("porteiro@mwm.com");
        leitor.setPassword(passwordEncoder.encode("leitor123"));
        leitor.setRole(User.Role.leitor);
        leitor.setFiliais(List.of("Toledo - PR"));
        userRepository.save(leitor);
    }

    // --- 2. COOPERADOS (Dados Reais do PDF) ---
    private void seedCooperadosReal() {
        System.out.println("   -> Criando Cooperados...");
        
        // Dados extraídos do cronograma_cooperados.pdf
        criarCooperado(102646L, "PRIMATO", "RENATO IVAN KUNZLER", "TERMINAÇÃO FRIMESA", "ABC-1111", "Inativo", "NÃO", "Fase Term. Firmesa");
        criarCooperado(511224L, "PRIMATO", "ADEMIR JOSE ENGELSING", "TERMINAÇÃO FRIMESA", "ABC-2222", "Ativo", "SIM", "Fase Term. Firmesa");
        criarCooperado(102284L, "PRIMATO", "ADEMIR MARCHIORO", "GRSC", "ABC-3333", "Ativo", "SIM", "GRSC");
        criarCooperado(102686L, "PRIMATO", "ARSENIO VICENTE WESCHENFELDER", "TERMINAÇÃO FRIMESA", "ABC-4444", "Ativo", "SIM", "Fase Term. Firmesa");
        criarCooperado(103034L, "PRIMATO", "CARLOS JAIME PAULY", "TERMINAÇÃO FRIMESA", "ABC-5555", "Ativo", "SIM", "Fase Crechário");
    }

    private void criarCooperado(Long matricula, String filial, String motorista, String tipo, String placa, String cert, String doa, String fase) {
        Cooperado c = new Cooperado();
        c.setMatricula(matricula);
        c.setFilial(filial);
        c.setMotorista(motorista);
        c.setTipoVeiculo(tipo);
        c.setPlaca(placa);
        c.setCertificado(cert);
        c.setDoamDejetos(doa);
        c.setFase(fase);
        cooperadoRepository.save(c);
    }

    // --- 3. AGENDA (Dados Reais do PDF) ---
    private void seedAgendaReal() {
        System.out.println("   -> Criando Agenda...");
        
        // Dados extraídos do cronograma_planejamentos.pdf
        criarAgenda("ADEMIR ENGELSING", "PRIMATO", 24.0, 0, 0, 0, 0, 0, 0, "Realizado");
        criarAgenda("ADEMIR MARCHIORO", "PRIMATO", 49.6, 4, 2, 0, 4, 0, 10, "Realizado");
        criarAgenda("ARSENIO WESCHENDELDER", "PRIMATO", 64.0, 10, 4, 4, 6, 0, 34, "Realizado");
        criarAgenda("CARLOS JAIME PAULY", "PRIMATO", 44.0, 10, 9, 10, 10, 10, 49, "Realizado");
        criarAgenda("DELCIO ROSSETTO", "AGROCAMPO", 32.0, 10, 10, 10, 10, 10, 50, "Planejado");
    }

    private void criarAgenda(String nome, String transp, Double km, int s, int t, int q, int qi, int sx, int qtd, String status) {
        Agenda a = new Agenda();
        a.setCooperado(nome);
        a.setTransportadora(transp);
        a.setKm(km);
        a.setSeg(s);
        a.setTer(t);
        a.setQua(q);
        a.setQui(qi);
        a.setSex(sx);
        a.setQtd(qtd);
        a.setStatus(status);
        agendaRepository.save(a);
    }

    // --- 4. PORTARIA (Mock Frontend) ---
    private void seedPortariaMock() {
        System.out.println("   -> Criando Registros de Portaria...");
        
        criarPortaria("Entregas", "2026-01-01", "10:00", "Primato", "Ademir Engelsing", "Caminhão de dejetos", "ABC-1D23", "Entrega de dejetos", "Concluido");
        criarPortaria("Abastecimentos", "2026-01-02", "09:30", "Transportadora XYZ", "Carlos Silva", "Caminhão Tanque", "DEF-4567", "Abastecimento de Diesel", "Concluido");
        criarPortaria("Entregas", "2026-01-01", "13:00", "Mosaic", "Renato Ivan", "Caminhão de entrega", "ABC-1D23", "Entrega de materiais", "Pendente");
    }

    private void criarPortaria(String cat, String data, String hora, String empresa, String mot, String tipo, String placa, String ativ, String status) {
        PortariaRegistro p = new PortariaRegistro();
        // Tenta converter a string para o Enum. Se falhar, usa um padrão.
        try {
            p.setCategoria(PortariaRegistro.Categoria.valueOf(cat));
        } catch (IllegalArgumentException e) {
            p.setCategoria(PortariaRegistro.Categoria.Entregas);
        }
        
        p.setData(LocalDate.parse(data));
        p.setHorario(LocalTime.parse(hora));
        p.setEmpresa(empresa);
        p.setMotorista(mot);
        p.setTipoVeiculo(tipo);
        p.setPlaca(placa);
        p.setAtividade(ativ);
        
        // Tratamento para acentos no Enum (Concluído -> Concluido)
        String statusLimpo = status.replace("ú", "u").replace("í", "i");
        try {
            p.setStatus(PortariaRegistro.Status.valueOf(statusLimpo));
        } catch (IllegalArgumentException e) {
            p.setStatus(PortariaRegistro.Status.Pendente);
        }
        
        portariaRepository.save(p);
    }

    // --- 5. ABASTECIMENTOS (Mock Frontend) ---
    private void seedAbastecimentosMock() {
        System.out.println("   -> Criando Abastecimentos...");
        criarAbastecimento("Primato Cooperativa", "Caminhão (Ração)", "BCK-0138", "Biometano", 134.56, 391396.0, "2025-09-25", "17:09:21", "17:09:21");
        criarAbastecimento("Primato Cooperativa", "Caminhão (Dejeto)", "BBW-9C55", "Biometano", 157.66, 370306.0, "2025-09-25", "17:08:56", "17:08:56");
    }

    private void criarAbastecimento(String cli, String veic, String placa, String prod, Double vol, Double odo, String data, String hIni, String hFim) {
        Abastecimento a = new Abastecimento();
        a.setCliente(cli);
        a.setVeiculo(veic);
        a.setPlaca(placa);
        a.setProduto(prod);
        a.setVolume(BigDecimal.valueOf(vol));
        a.setOdometro(BigDecimal.valueOf(odo));
        a.setData(LocalDate.parse(data));
        a.setHoraInicio(LocalTime.parse(hIni));
        a.setHoraTermino(LocalTime.parse(hFim));
        a.setStatus("Concluído");
        a.setUsuario("vanessa");
        abastecimentoRepository.save(a);
    }

    // --- 6. COLETAS (Mock Frontend) ---
    private void seedColetasMock() {
        System.out.println("   -> Criando Coletas...");
        criarColeta("Primato", "Luiz Carlos", "Caminhão de dejetos", "ABC-1D23", 123456.0, "2025-01-01", "15:00", "Pendente");
    }

    private void criarColeta(String coop, String mot, String tipo, String placa, Double odo, String data, String hora, String status) {
        Coleta c = new Coleta();
        c.setCooperado(coop);
        c.setMotorista(mot);
        c.setTipoVeiculo(tipo);
        c.setPlaca(placa);
        c.setOdometro(odo);
        c.setDataPrevisao(LocalDate.parse(data));
        c.setHoraPrevisao(LocalTime.parse(hora));
        c.setStatus(status);
        coletaRepository.save(c);
    }

    // --- 7. QUALIDADE (Mock Frontend) ---
    private void seedQualidadeMock() {
        System.out.println("   -> Criando Análises de Qualidade...");
        QualidadeDejetos q1 = new QualidadeDejetos();
        q1.setDataColeta(LocalDate.parse("2025-10-13"));
        q1.setCooperado("Ademir Engelsing");
        q1.setPlaca("ABC-1D23");
        q1.setPh(7.2);
        q1.setDensidade(1025.0);
        q1.setEntregaReferencia("ENT-54321");
        qualidadeRepository.save(q1);
    }
}