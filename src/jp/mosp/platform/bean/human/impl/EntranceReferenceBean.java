/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.EntranceReferenceBeanInterface;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;

/**
 * 人事入社情報参照クラス。
 */
public class EntranceReferenceBean extends PlatformBean implements EntranceReferenceBeanInterface {
	
	/**
	 * 人事入社情報DAO。
	 */
	protected EntranceDaoInterface entranceDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public EntranceReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		entranceDao = createDaoInstance(EntranceDaoInterface.class);
	}
	
	@Override
	public Date getEntranceDate(String personalId) throws MospException {
		EntranceDtoInterface dto = getEntranceInfo(personalId);
		if (dto != null) {
			return dto.getEntranceDate();
		}
		return null;
	}
	
	@Override
	public EntranceDtoInterface getEntranceInfo(String personalId) throws MospException {
		return entranceDao.findForInfo(personalId);
	}
	
	@Override
	public boolean isEntered(String personalId, Date targetDate) throws MospException {
		EntranceDtoInterface dto = getEntranceInfo(personalId);
		if (dto != null) {
			return targetDate.compareTo(dto.getEntranceDate()) >= 0;
		}
		return false;
	}
	
}
