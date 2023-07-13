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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;

/**
 * 休日出勤申請検索インターフェース。
 */
public interface WorkOnHolidayRequestSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から休日出勤申請を取得する。<br><br>
	 * 設定された条件で、検索を行う。
	 * @return 休日出勤申請
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<WorkOnHolidayRequestListDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param substitute セットする 振替休暇
	 */
	void setSubstitute(String substitute);
	
	/**
	 * @param substituteRange セットする 振替休暇(長さ)
	 */
	void setSubstituteRange(String substituteRange);
	
	/**
	 * @param workflowStatus セットする 状態。
	 */
	void setWorkflowStatus(String workflowStatus);
	
	/**
	 * @param requestStartDate セットする 表示開始日。
	 */
	void setRequestStartDate(Date requestStartDate);
	
	/**
	 * @param requestEndDate セットする 表示終了日。
	 */
	void setRequestEndDate(Date requestEndDate);
	
}
