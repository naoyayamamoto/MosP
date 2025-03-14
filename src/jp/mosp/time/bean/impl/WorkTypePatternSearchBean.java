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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.WorkTypePatternSearchBeanInterface;
import jp.mosp.time.dao.settings.WorkTypePatternDaoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;

/**
 * 勤務形態パターン検索クラス。
 */
public class WorkTypePatternSearchBean extends PlatformBean implements WorkTypePatternSearchBeanInterface {
	
	/**
	 * 勤務形態パターンDAO
	 */
	protected WorkTypePatternDaoInterface	dao;
	
	/**
	 * 有効日。
	 */
	private Date							activateDate;
	
	/**
	 * パターンコード。
	 */
	private String							patternCode;
	
	/**
	 * パターン名称。
	 */
	private String							patternName;
	
	/**
	 * パターン略称。
	 */
	private String							patternAbbr;
	
	/**
	 * 有効無効フラグ。
	 */
	private String							inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public WorkTypePatternSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypePatternDaoInterface.class);
	}
	
	@Override
	public List<WorkTypePatternDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("patternCode", patternCode);
		param.put("patternName", patternName);
		param.put("patternAbbr", patternAbbr);
		param.put("inactivateFlag", inactivateFlag);
		// 検索
		return dao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	
	@Override
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	
	@Override
	public void setPatternAbbr(String patternAbbr) {
		this.patternAbbr = patternAbbr;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
