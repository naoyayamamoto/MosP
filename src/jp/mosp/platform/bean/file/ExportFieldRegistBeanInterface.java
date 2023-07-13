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
package jp.mosp.platform.bean.file;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;

/**
 * エクスポートフィールドマスタ登録インターフェース。<br>
 */
public interface ExportFieldRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	ExportFieldDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param exportCode エクスポートコード
	 * @param inactivateFlag 無効フラグ
	 * @param fieldArray フィールド配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(String exportCode, String inactivateFlag, String[] fieldArray) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param exportCode エクスポートコード
	 * @param inactivateFlag 無効フラグ
	 * @param fieldArray フィールド配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void update(String exportCode, String inactivateFlag, String[] fieldArray) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * @param exportCode エクスポートコード
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(String exportCode) throws MospException;
	
}
