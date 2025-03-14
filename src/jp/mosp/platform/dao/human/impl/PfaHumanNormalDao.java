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
package jp.mosp.platform.dao.human.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanNormalDaoInterface;
import jp.mosp.platform.dto.human.HumanNormalDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanNormalDto;

/**
 * 人事汎用通常情報DAOクラス。
 */
public class PfaHumanNormalDao extends PlatformDao implements HumanNormalDaoInterface {
	
	/**
	 * 人事入社情報。
	 */
	public static final String	TABLE					= "pfa_human_normal";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_HUMAN_NORMAL_ID	= "pfa_human_normal_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 人事項目区分。
	 */
	public static final String	COL_HUMAN_ITEM_TYPE		= "human_item_type";
	
	/**
	 * 人事項目値。
	 */
	public static final String	COL_HUMAN_ITEM_VALUE	= "human_item_value";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFA_HUMAN_NORMAL_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanNormalDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanNormalDto dto = new PfaHumanNormalDto();
		dto.setPfaHumanNormalId(getLong(COL_PFA_HUMAN_NORMAL_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setHumanItemType(getString(COL_HUMAN_ITEM_TYPE));
		dto.setHumanItemValue(getString(COL_HUMAN_ITEM_VALUE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HumanNormalDtoInterface> mappingAll() throws MospException {
		List<HumanNormalDtoInterface> all = new ArrayList<HumanNormalDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		HumanNormalDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanNormalId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getHumanItemType());
		setParam(index++, dto.getHumanItemValue());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			HumanNormalDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanNormalId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			HumanNormalDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanNormalId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected HumanNormalDtoInterface castDto(BaseDtoInterface baseDto) {
		return (HumanNormalDtoInterface)baseDto;
	}
	
	@Override
	public HumanNormalDtoInterface findForInfo(String itemName, String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HUMAN_ITEM_TYPE));
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			prepareStatement(sb.toString());
			setParam(index++, itemName);
			setParam(index++, personalId);
			executeQuery();
			HumanNormalDtoInterface dto = null;
			if (rs.next()) {
				dto = castDto(mapping());
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<HumanNormalDtoInterface> findForInfoNotIn(List<String> itemNames) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(notIn(COL_HUMAN_ITEM_TYPE, itemNames.size()));
			prepareStatement(sb.toString());
			setParamsIn(MospUtility.toArray(itemNames));
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<HumanNormalDtoInterface> findForInfoForValue(String itemName, String itemValue) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HUMAN_ITEM_TYPE));
			sb.append(and());
			sb.append(equal(COL_HUMAN_ITEM_VALUE));
			prepareStatement(sb.toString());
			setParam(index++, itemName);
			setParam(index++, itemValue);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
