package com.phantergaming.usal.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    @Id
    @Column(unique = true)
    @NotEmpty
    private String numeracion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "domicilio_fiscal")
    private String domicilioFiscal;

    private String pais;

    @Column(name = "telefono_atencion")
    private String telefonoAtencion;

    @Column(name = "telefono_personal")
    private String telefonoPersonal;

    private String email;

    @PrePersist()
    public void prePersist() {
        this.nombreEmpresa = "Phanter Gaming S.A";
        this.domicilioFiscal = "Rio Hondo S/N";
        this.pais = "Argentina";
        this.telefonoPersonal = "011 15 7123-4587";
        this.telefonoAtencion = "0800-022-1244";
        this.email = "phantergaming@hotmail.com";
        this.createAt = new Date();
    }


    public String getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefonoAtencion() {
        return telefonoAtencion;
    }

    public void setTelefonoAtencion(String telefonoAtencion) {
        this.telefonoAtencion = telefonoAtencion;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura)) return false;
        Factura factura = (Factura) o;
        return Objects.equals(numeracion, factura.numeracion) &&
                Objects.equals(createAt, factura.createAt) &&
                Objects.equals(nombreEmpresa, factura.nombreEmpresa) &&
                Objects.equals(domicilioFiscal, factura.domicilioFiscal) &&
                Objects.equals(pais, factura.pais) &&
                Objects.equals(telefonoAtencion, factura.telefonoAtencion) &&
                Objects.equals(telefonoPersonal, factura.telefonoPersonal) &&
                Objects.equals(email, factura.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeracion, createAt, nombreEmpresa, domicilioFiscal, pais, telefonoAtencion, telefonoPersonal, email);
    }
}
