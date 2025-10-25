package com.mipymes.sistema_contable.controller;

import com.mipymes.sistema_contable.model.Account;
import com.mipymes.sistema_contable.repository.AccountRepository;
import com.mipymes.sistema_contable.repository.JournalLineRepository;
import com.mipymes.sistema_contable.service.PostingService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final AccountRepository accountRepository;
    private final JournalLineRepository journalLineRepository;
    public ReportController(AccountRepository accountRepository, JournalLineRepository journalLineRepository, PostingService postingService) {
        this.accountRepository = accountRepository;
        this.journalLineRepository = journalLineRepository;
    }

    @GetMapping("/balance-comprobacion")
    public List<Map<String, Object>> balanceComprobacion() {
        List<Account> cuentas = accountRepository.findAll();
        List<Map<String, Object>> res = new ArrayList<>();
        for (Account c : cuentas) {
            BigDecimal debe = journalLineRepository.sumDebeByCuenta(c.getId());
            BigDecimal haber = journalLineRepository.sumHaberByCuenta(c.getId());
            Map<String,Object> m = new HashMap<>();
            m.put("codigo", c.getCodigo());
            m.put("cuenta", c.getNombre());
            m.put("debe", debe);
            m.put("haber", haber);
            res.add(m);
        }
        return res;
    }

    @GetMapping("/balance-general")
    public Map<String, Object> balanceGeneral() {
        BigDecimal activos = BigDecimal.ZERO;
        BigDecimal pasivos = BigDecimal.ZERO;
        BigDecimal patrimonio = BigDecimal.ZERO;
        for (Account c : accountRepository.findAll()) {
            BigDecimal saldo = journalLineRepository.sumDebeByCuenta(c.getId()).subtract(journalLineRepository.sumHaberByCuenta(c.getId()));
            switch (c.getTipo()) {
                case ACTIVO: activos = activos.add(saldo); break;
                case PASIVO: pasivos = pasivos.add(saldo); break;
                case PATRIMONIO: patrimonio = patrimonio.add(saldo); break;
                default: break;
            }
        }
        Map<String,Object> out = new HashMap<>();
        out.put("activos", activos);
        out.put("pasivos", pasivos);
        out.put("patrimonio", patrimonio);
        return out;
    }

    @GetMapping("/estado-resultados")
    public Map<String, Object> estadoResultados() {
        BigDecimal ingresos = BigDecimal.ZERO;
        BigDecimal gastos = BigDecimal.ZERO;
        for (Account c : accountRepository.findAll()) {
            BigDecimal saldo = journalLineRepository.sumDebeByCuenta(c.getId()).subtract(journalLineRepository.sumHaberByCuenta(c.getId()));
            if (c.getTipo().name().equals("INGRESO")) ingresos = ingresos.add(saldo.negate()); // ingresos típicamente crédito
            if (c.getTipo().name().equals("GASTO") || c.getTipo().name().equals("GASTOS")) gastos = gastos.add(saldo); // gastos típicamente débito
        }
        BigDecimal resultado = ingresos.subtract(gastos);
        Map<String,Object> out = new HashMap<>();
        out.put("ingresos", ingresos);
        out.put("gastos", gastos);
        out.put("resultado", resultado);
        return out;
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel(@RequestParam(defaultValue = "balance-comprobacion") String type,
                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) throws Exception {
        // Para simplicidad exporta Balance de comprobación actual
        List<Map<String,Object>> rows = balanceComprobacion();

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("BalanceComprobacion");
        int r = 0;
        Row header = sheet.createRow(r++);
        header.createCell(0).setCellValue("Código");
        header.createCell(1).setCellValue("Cuenta");
        header.createCell(2).setCellValue("Debe");
        header.createCell(3).setCellValue("Haber");

        for (Map<String,Object> map : rows) {
            Row row = sheet.createRow(r++);
            row.createCell(0).setCellValue(Objects.toString(map.get("codigo"), ""));
            row.createCell(1).setCellValue(Objects.toString(map.get("cuenta"), ""));
            row.createCell(2).setCellValue(((BigDecimal)map.get("debe")).doubleValue());
            row.createCell(3).setCellValue(((BigDecimal)map.get("haber")).doubleValue());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wb.write(baos);
        wb.close();
        byte[] bytes = baos.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=balance_comprobacion.xlsx");
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @PostMapping("/posting")
    public ResponseEntity<?> postingAll() {
        return ResponseEntity.ok("Posting al mayor deshabilitado temporalmente");
    }
}
