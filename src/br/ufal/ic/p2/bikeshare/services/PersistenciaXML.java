package br.ufal.ic.p2.bikeshare.services;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import br.ufal.ic.p2.bikeshare.exceptions.PersistenciaException;

public class PersistenciaXML {
    private static final String ARQUIVO_DADOS = "bikeshare_dados.xml";

    public void salvar(EstadoSistema estado) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(ARQUIVO_DADOS)))) {
            encoder.writeObject(estado);
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao salvar dados do sistema.", e);
        }
    }

    public EstadoSistema carregar() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return new EstadoSistema();
        }
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj = decoder.readObject();
            if (!(obj instanceof EstadoSistema)) {
                throw new PersistenciaException("Arquivo de dados corrompido.", null);
            }
            return (EstadoSistema) obj;
        } catch (IOException | RuntimeException e) {
            if (e instanceof PersistenciaException) {
                throw (PersistenciaException) e;
            }
            throw new PersistenciaException("Erro ao carregar dados do sistema.", e);
        }
    }

    public void limparArquivo() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}