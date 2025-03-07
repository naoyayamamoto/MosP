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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.EmploymentContractReferenceBeanInterface;
import jp.mosp.platform.dao.system.EmploymentContractDaoInterface;
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;

/**
 * 雇用契約マスタ参照クラス。
 */
public class EmploymentContractReferenceBean extends PlatformBean implements EmploymentContractReferenceBeanInterface {
	
	/**
	 * 雇用契約マスタDAO。
	 */
	protected EmploymentContractDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public EmploymentContractReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(EmploymentContractDaoInterface.class);
	}
	
	@Override
	public List<EmploymentContractDtoInterface> getContractHistory(String employmentContractCode) throws MospException {
		return dao.findForHistory(employmentContractCode);
	}
	
	@Override
	public EmploymentContractDtoInterface getContractInfo(String employmentContractCode, Date targetDate)
			throws MospException {
		return dao.findForInfo(employmentContractCode, targetDate);
	}
	
	@Override
	public String getContractName(String employmentContractCode, Date targetDate) throws MospException {
		EmploymentContractDtoInterface dto = getContractInfo(employmentContractCode, targetDate);
		if (dto == null) {
			return employmentContractCode;
		}
		return dto.getEmploymentContractName();
	}
	
	@Override
	public String getContractAbbr(String employmentContractCode, Date targetDate) throws MospException {
		EmploymentContractDtoInterface dto = getContractInfo(employmentContractCode, targetDate);
		if (dto == null) {
			return employmentContractCode;
		}
		return dto.getEmploymentContractAbbr();
	}
	
	@Override
	public List<EmploymentContractDtoInterface> getContractList(Date targetDate, String operationType)
			throws MospException {
		String[] rangeArray = getRangeEmploymentContract(operationType, targetDate);
		return dao.findForActivateDate(targetDate, rangeArray);
	}
	
	@Override
	public String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType) throws MospException {
		// プルダウン用配列取得(略称)
		return getSelectArray(targetDate, needBlank, operationType, false, false);
	}
	
	@Override
	public String[][] getNameSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(名称)
		return getSelectArray(targetDate, needBlank, operationType, true, false);
	}
	
	@Override
	public String[][] getCodedAbbrSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード+略称)
		return getSelectArray(targetDate, needBlank, operationType, false, true);
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank, String operationType)
			throws MospException {
		// プルダウン用配列取得(コード+名称)
		return getSelectArray(targetDate, needBlank, operationType, true, true);
	}
	
	@Override
	public EmploymentContractDtoInterface findForKey(String employmentContractCode, Date activateDate)
			throws MospException {
		return dao.findForKey(employmentContractCode, activateDate);
	}
	
	/**
	 * プルダウン用配列を取得する。<br>
	 * @param targetDate    対象年月日
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @param operationType 操作範囲権限
	 * @param isName        名称表示(true：名称表示、false：略称表示)
	 * @param viewCode      コード表示(true：コード表示、false：コード非表示)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[][] getSelectArray(Date targetDate, boolean needBlank, String operationType, boolean isName,
			boolean viewCode) throws MospException {
		// 一覧取得
		List<EmploymentContractDtoInterface> list = getContractList(targetDate, operationType);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// コード最大長取得
		int length = getMaxCodeLength(list, viewCode);
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// プルダウン用配列作成
		for (EmploymentContractDtoInterface dto : list) {
			// コード設定
			array[idx][0] = dto.getEmploymentContractCode();
			// 表示内容設定
			if (isName && viewCode) {
				// コード+名称
				array[idx++][1] = getCodedName(dto.getEmploymentContractCode(), dto.getEmploymentContractName(),
						length);
			} else if (isName) {
				// 名称
				array[idx++][1] = dto.getEmploymentContractName();
			} else if (viewCode) {
				// コード+略称
				array[idx++][1] = getCodedName(dto.getEmploymentContractCode(), dto.getEmploymentContractAbbr(),
						length);
			} else {
				// 略称
				array[idx++][1] = dto.getEmploymentContractAbbr();
			}
		}
		return array;
	}
	
	/**
	 * リスト中のDTOにおけるコード最大文字数を取得する。<br>
	 * @param list     対象リスト
	 * @param viewCode コード表示(true：コード表示、false：コード非表示)
	 * @return リスト中のDTOにおけるコード最大文字数
	 */
	protected int getMaxCodeLength(List<EmploymentContractDtoInterface> list, boolean viewCode) {
		// コード表示確認
		if (viewCode == false) {
			return 0;
		}
		// コード最大文字数
		int length = 0;
		// コード最大文字数確認
		for (EmploymentContractDtoInterface dto : list) {
			if (dto.getEmploymentContractCode().length() > length) {
				length = dto.getEmploymentContractCode().length();
			}
		}
		return length;
	}
	
}
