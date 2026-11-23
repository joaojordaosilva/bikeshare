package br.ufal.ic.p2.bikeshare.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import br.ufal.ic.p2.bikeshare.exceptions.DadoInvalidoException;

public class DataUtil {
    private static final DateTimeFormatter FORMATO_DATA_HORA =
            DateTimeFormatter.ofPattern("d/M/uuuu H:mm").withResolverStyle(ResolverStyle.STRICT);

    public static LocalDateTime parseDataHora(String dataStr) {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            throw new DadoInvalidoException("Data nao pode ser nula.");
        }
        try {
            return LocalDateTime.parse(dataStr.trim(), FORMATO_DATA_HORA);
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoException("Data invalida.");
        }
    }

    public static String formatarDataHora(LocalDateTime dataHora) {
        return dataHora.format(FORMATO_DATA_HORA);
    }
}