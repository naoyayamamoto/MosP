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
package jp.mosp.framework.comparator;

import java.util.Comparator;

import jp.mosp.framework.base.IndexedDtoInterface;

/**
 * {@link IndexedDtoInterface}の比較をするクラス。
 */
public class IndexComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		IndexedDtoInterface dto1 = (IndexedDtoInterface)o1;
		IndexedDtoInterface dto2 = (IndexedDtoInterface)o2;
		return dto1.getIndex() - dto2.getIndex();
	}
	
}
