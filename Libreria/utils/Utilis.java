package com.ve.bc.openbanking.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class Utilis {
	public String generarCodigoTracerId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
