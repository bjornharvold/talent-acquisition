package com.tps.tpi.utils;

import com.tps.tpi.model.objects.dto.AbstractDto;
import com.tps.tpi.model.objects.dto.RegionDto;
import org.apache.commons.lang.StringUtils;

import java.util.Comparator;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 23, 2009
 * Time: 2:45:11 AM
 * Responsibility: This will sort collection based on position of id in list
 */
public class IdComparator implements Comparator<AbstractDto> {
    private final List<String> ids;

    public IdComparator(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public int compare(AbstractDto dto1, AbstractDto dto2) {
        int result = 0;
        int dto1Pos = 0;
        int dto2Pos = 0;

        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);

            if (StringUtils.equals(id, dto1.getId())) {
                dto1Pos = i;
            } else if (StringUtils.equals(id, dto2.getId())) {
                dto2Pos = i;
            }
        }

        if (dto1Pos < dto2Pos) {
            result = -1;
        } else if (dto1Pos == dto2Pos) {
            result = 0;
        } else if (dto1Pos > dto2Pos) {
            result = 1;
        }

        return result;
    }
}
