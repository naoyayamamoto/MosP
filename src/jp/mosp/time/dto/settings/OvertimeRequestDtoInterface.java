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
/**
 * 
 */
package jp.mosp.time.dto.settings;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.dto.base.RequestDateDtoInterface;

/**
 * 残業申請DTOインターフェース
 */
public interface OvertimeRequestDtoInterface
		extends BaseDtoInterface, PersonalIdDtoInterface, WorkflowNumberDtoInterface, RequestDateDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdOvertimeRequestId();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 申請時間。
	 */
	int getRequestTime();
	
	/**
	 * @return 残業区分。
	 */
	int getOvertimeType();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdOvertimeRequestId セットする レコード識別ID。
	 */
	void setTmdOvertimeRequestId(long tmdOvertimeRequestId);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param requestTime セットする 申請時間。
	 */
	void setRequestTime(int requestTime);
	
	/**
	 * @param overtimeType セットする 残業区分。
	 */
	void setOvertimeType(int overtimeType);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
