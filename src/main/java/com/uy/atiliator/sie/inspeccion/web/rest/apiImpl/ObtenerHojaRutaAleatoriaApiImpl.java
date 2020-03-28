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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        if (hojasRuta.isEmpty()) {
            return ResponseEntity.ok().body(buscarRandom(hojasRuta));
        } else {
            throw new hojaRutaNotFoundException();
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
        HojaRutaAleatoria response = new HojaRutaAleatoria()
            .id(hojaRuta.getId().intValue())
            .descripcion(hojaRuta.getDescripcion())
            .tipoInspecion(hojaRuta.getTipoInspeccion().name())
            .fecha(hojaRuta.getFecha().toString())
            .fechaHora(hojaRuta.getFechaHoraUTC().toString())
            .titulo(hojaRuta.getTitulo())
            .prioridad(hojaRuta.getPriodidad())
            ;
        return response;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Ho hay hojas de rutas diponibles")
    public class hojaRutaNotFoundException extends RuntimeException {
    }

}
