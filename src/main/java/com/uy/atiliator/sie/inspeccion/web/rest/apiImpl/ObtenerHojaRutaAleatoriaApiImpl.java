package com.uy.atiliator.sie.inspeccion.web.rest.apiImpl;

import com.uy.atiliator.sie.inspeccion.config.AsyncConfiguration;
import com.uy.atiliator.sie.inspeccion.domain.HojaRuta;
import com.uy.atiliator.sie.inspeccion.repository.HojaRutaRepository;
import com.uy.atiliator.sie.inspeccion.web.api.ObtenerHojaRutaAleatoriaApi;
import com.uy.atiliator.sie.inspeccion.web.api.ObtenerHojaRutaAleatoriaApiDelegate;
import com.uy.atiliator.sie.inspeccion.web.api.model.HojaRutaAleatoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerHojaRutaAleatoriaApiImpl implements ObtenerHojaRutaAleatoriaApiDelegate {

    @Autowired
    HojaRutaRepository hojaRutaRepository;

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Override
    public ResponseEntity<List<HojaRutaAleatoria>> obtenerHojaRutaAleatoriaGet() {
        log.debug("Atiliator ---> ObtenerHojaRutaAleatoriaApiImpl.obtenerHojaRutaAleatoriaGet");
        List<HojaRuta> hojasRuta = hojaRutaRepository.findAll();
        log.debug("Atiliator ---> hojasRuta.isEmpty()? " + hojasRuta.isEmpty());
        if (!hojasRuta.isEmpty()) {
            return ResponseEntity.ok().body(buscarRandom(hojasRuta));
        } else {
            return null;
        }
    }

    private List<HojaRutaAleatoria> buscarRandom(List<HojaRuta> hojasRuta ) {
        int cantidad = hojasRuta.size();
        int numero = (int) (Math.random() * cantidad) + 1;
        log.debug("Atiliator ---> cantidad:" + cantidad);
        HojaRuta hojaRutaSeleccionada = hojasRuta.get(numero);
        log.debug("Atiliator ---> hojaRutaSeleccionada:" + hojaRutaSeleccionada.toString());
        List response = new ArrayList();
        response.add(createHojaRutaAletatoria(hojaRutaSeleccionada));
        return response;
    }

    private HojaRutaAleatoria createHojaRutaAletatoria(HojaRuta hojaRuta) {
        HojaRutaAleatoria response = new HojaRutaAleatoria();

        response.setId(hojaRuta.getId().intValue());
        response.descripcion(hojaRuta.getDescripcion());
        response.tipoInspecion(hojaRuta.getTipoInspeccion().name());
        response.fecha(hojaRuta.getFecha().toString());
        response.fechaHora(hojaRuta.getFechaHoraUTC().toString());
        response.titulo(hojaRuta.getTitulo());
        response.prioridad(hojaRuta.getPriodidad());

        return response;
    }
}
