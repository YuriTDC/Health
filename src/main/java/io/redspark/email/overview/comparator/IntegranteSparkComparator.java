package io.redspark.email.overview.comparator;

import io.redspark.email.overview.dto.IntegranteSparksDTO;

import java.util.Comparator;

public class IntegranteSparkComparator implements Comparator<IntegranteSparksDTO> {

    @Override
    public int compare(IntegranteSparksDTO o1, IntegranteSparksDTO o2) {
        return o1.getNome().compareToIgnoreCase(o2.getNome());
    }
    
}