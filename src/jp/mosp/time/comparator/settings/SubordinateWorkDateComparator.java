/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.dto.settings.SubordinateListDtoInterface;

/**
 *
 */
public class SubordinateWorkDateComparator implements Comparator<SubordinateListDtoInterface> {
	
	@Override
	public int compare(SubordinateListDtoInterface dto1, SubordinateListDtoInterface dto2) {
		if (dto1.getWorkDate() == null && dto2.getWorkDate() == null) {
			return 0;
		}
		if (dto1.getWorkDate() == null) {
			return -1;
		}
		if (dto2.getWorkDate() == null) {
			return 1;
		}
		return Double.compare(dto1.getWorkDate(), dto2.getWorkDate());
	}
	
}
