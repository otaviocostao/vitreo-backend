package com.api.vitreo.dto.dashboard;

import java.util.List;

public record DashboardResponseDTO(
        MetricaCardDTO vendasNoPeriodo,
        MetricaCardDTO valorTotalVendas,
        MetricaCardDTO ticketMedio,
        MetricaCardDTO novosClientes,
        List<GraficoReceitaDTO> graficoReceita,
        List<VendaRecenteDTO> ultimasVendas
) {
}
