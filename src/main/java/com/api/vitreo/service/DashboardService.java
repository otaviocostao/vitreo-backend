package com.api.vitreo.service;

import com.api.vitreo.dto.dashboard.*;
import com.api.vitreo.entity.Pedido;
import com.api.vitreo.repository.ClienteRepository;
import com.api.vitreo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public DashboardResponseDTO getDashboardData(LocalDate dataInicio, LocalDate dataFim) {

        long diasNoPeriodo = ChronoUnit.DAYS.between(dataInicio, dataFim);
        LocalDate fimPeriodoAnterior = dataInicio.minusDays(1);
        LocalDate inicioPeriodoAnterior = fimPeriodoAnterior.minusDays(diasNoPeriodo);

        LocalDateTime inicioAtual = dataInicio.atStartOfDay();
        LocalDateTime fimAtual = dataFim.atTime(LocalTime.MAX);

        Long vendasAtuais = pedidoRepository.countByDataPedidoBetween(inicioAtual, fimAtual);
        BigDecimal valorTotalAtual = pedidoRepository.sumValorFinalByDataPedidoBetween(inicioAtual, fimAtual).orElse(BigDecimal.ZERO);
        BigDecimal ticketMedioAtual = (vendasAtuais > 0) ? valorTotalAtual.divide(new BigDecimal(vendasAtuais), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        Long novosClientesAtuais = clienteRepository.countByDataCadastroBetween(inicioAtual, fimAtual);

        LocalDateTime inicioAnterior = inicioPeriodoAnterior.atStartOfDay();
        LocalDateTime fimAnterior = fimPeriodoAnterior.atTime(LocalTime.MAX);

        Long vendasAnteriores = pedidoRepository.countByDataPedidoBetween(inicioAnterior, fimAnterior);
        BigDecimal valorTotalAnterior = pedidoRepository.sumValorFinalByDataPedidoBetween(inicioAnterior, fimAnterior).orElse(BigDecimal.ZERO);
        BigDecimal ticketMedioAnterior = (vendasAnteriores > 0) ? valorTotalAnterior.divide(new BigDecimal(vendasAnteriores), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        Long novosClientesAnteriores = clienteRepository.countByDataCadastroBetween(inicioAnterior, fimAnterior);

        MetricaCardDTO vendasCard = construirMetricaCard(new BigDecimal(vendasAtuais), new BigDecimal(vendasAnteriores), "contagem", "vendas");
        MetricaCardDTO valorTotalCard = construirMetricaCard(valorTotalAtual, valorTotalAnterior, "valor", "");
        MetricaCardDTO ticketMedioCard = construirMetricaCard(ticketMedioAtual, ticketMedioAnterior, "valor", "");
        MetricaCardDTO novosClientesCard = construirMetricaCard(new BigDecimal(novosClientesAtuais), new BigDecimal(novosClientesAnteriores), "contagem", "cliente");

        List<Object[]> resultadosRaw = pedidoRepository.findReceitaPorDiaAsObjectArray(inicioAtual, fimAtual);

        List<GraficoReceitaDTO> dadosGrafico = resultadosRaw.stream()
                .map(resultado -> {
                    java.sql.Date dataSql = (java.sql.Date) resultado[0];
                    LocalDate data = dataSql.toLocalDate();

                    BigDecimal valor = (BigDecimal) resultado[1];

                    return new GraficoReceitaDTO(data, valor);
                })
                .collect(Collectors.toList());


        List<VendaRecenteDTO> ultimasVendas = pedidoRepository.findTop4ByOrderByDataPedidoDesc().stream()
                .map(this::toVendaRecenteDTO)
                .collect(Collectors.toList());

        return new DashboardResponseDTO(
                vendasCard,
                valorTotalCard,
                ticketMedioCard,
                novosClientesCard,
                dadosGrafico,
                ultimasVendas
        );
    }

    private MetricaCardDTO construirMetricaCard(BigDecimal valorAtual, BigDecimal valorAnterior, String tipo, String contextoUnidade) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();

        String valorFormatado = tipo.equals("valor") ? currencyFormat.format(valorAtual) : numberFormat.format(valorAtual);

        if (valorAnterior.compareTo(BigDecimal.ZERO) == 0) {
            return new MetricaCardDTO(valorFormatado, "up", 100.0, "+ " + valorFormatado + " vs. período anterior");
        }

        BigDecimal diferenca = valorAtual.subtract(valorAnterior);

        String tendencia = diferenca.compareTo(BigDecimal.ZERO) >= 0 ? "up" : "down";

        BigDecimal percentualDecimal = diferenca.divide(valorAnterior, 4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).abs();
        Double percentual = percentualDecimal.doubleValue();

        String sinal = diferenca.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "";
        String diferencaFormatada = tipo.equals("valor") ? currencyFormat.format(diferenca) : numberFormat.format(diferenca);
        String textoContexto = String.format("%s %s %s vs. período anterior", sinal, diferencaFormatada, contextoUnidade);

        return new MetricaCardDTO(valorFormatado, tendencia, percentual, textoContexto);
    }

    private VendaRecenteDTO toVendaRecenteDTO(Pedido pedido) {
        return new VendaRecenteDTO(
                pedido.getId(),
                pedido.getCliente().getNomeCompleto(),
                pedido.getValorFinal(),
                pedido.getDataPedido()
        );
    }
}
