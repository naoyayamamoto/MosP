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
package jp.mosp.platform.bean.file;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ImportDtoInterface;

/**
 * インポートマスタ参照インターフェース。
 */
public interface ImportReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * インポートマスタからレコードを取得する。<br>
	 * インポートコードで合致するレコードが無い場合、nullを返す。<br>
	 * @param importCode インポートコード
	 * @return インポートマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ImportDtoInterface findForKey(String importCode) throws MospException;
	
}
