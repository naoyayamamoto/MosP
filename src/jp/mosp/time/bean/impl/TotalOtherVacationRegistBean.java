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

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.TotalOtherVacationRegistBeanInterface;
import jp.mosp.time.dao.settings.TotalOtherVacationDaoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalOtherVacationDto;

/**
 * 勤怠集計その他休暇データ登録クラス。
 */
public class TotalOtherVacationRegistBean extends PlatformBean implements TotalOtherVacationRegistBeanInterface {
	
	/**
	 * 休暇データDAOクラス。<br>
	 */
	protected TotalOtherVacationDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public TotalOtherVacationRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(TotalOtherVacationDaoInterface.class);
	}
	
	@Override
	public TotalOtherVacationDtoInterface getInitDto() {
		return new TmdTotalOtherVacationDto();
	}
	
	@Override
	public void insert(TotalOtherVacationDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTotalOtherVacationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(TotalOtherVacationDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdTotalOtherVacationId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdTotalOtherVacationId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void regist(TotalOtherVacationDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getPersonalId(), dto.getCalculationYear(), dto.getCalculationMonth(),
				dto.getHolidayCode()) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 更新
			update(dto);
		}
	}
	
	@Override
	public void delete(String personalId, int calculationYear, int calculationMonth) throws MospException {
		List<TotalOtherVacationDtoInterface> list = dao.findForList(personalId, calculationYear, calculationMonth);
		for (TotalOtherVacationDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdTotalOtherVacationId());
		}
	}
	
	@Override
	public void delete(List<String> personalIdList, int calculationYear, int calculationMonth) throws MospException {
		for (String personalId : personalIdList) {
			delete(personalId, calculationYear, calculationMonth);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(TotalOtherVacationDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKey(dto.getPersonalId(), dto.getCalculationYear(), dto.getCalculationMonth(),
				dto.getHolidayCode()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(TotalOtherVacationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdTotalOtherVacationId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(TotalOtherVacationDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdTotalOtherVacationId());
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(TotalOtherVacationDtoInterface dto) {
		// 妥当性確認
	}
	
}
