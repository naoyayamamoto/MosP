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
package jp.mosp.platform.human.action;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.BinaryUtility;
import jp.mosp.framework.utils.NameUtility;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.framework.utils.TopicPathUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.human.HumanBinaryNormalReferenceBeanInterface;
import jp.mosp.platform.bean.human.HumanBinaryNormalRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanBinaryNormalDtoInterface;
import jp.mosp.platform.human.base.PlatformHumanAction;
import jp.mosp.platform.human.constant.PlatformHumanConst;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.human.vo.HumanBinaryNormalCardVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事汎用バイナリ通常情報で表示・登録・削除を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_SEARCH}
 * </li><li>
 * {@link #CMD_INSERT}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li><li>
 * {@link #CMD_DELETE}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li></ul>
 */
public class HumanBinaryNormalCardAction extends PlatformHumanAction {
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 画面を表示する。<br>
	 */
	public static final String	CMD_SELECT					= "PF1551";
	
	/**
	 * 検索コマンド。<br>
	 * <br>
	 * 社員コード入力欄に入力された社員コードで検索を行い、
	 * 該当する社員の人事汎用通常情報登録画面へ遷移する。<br>
	 * 社員コード順にページを送るボタンがクリックされた場合には
	 * 遷移元の社員一覧リスト検索結果を参照して前後それぞれページ移動を行う。<br>
	 * 入力した社員コードが存在しない、または入力されていない状態で
	 * 「検索ボタン」がクリックされた場合はエラーメッセージにて通知。<br>
	 * 現在表示されている社員の社員コードの前後にデータが存在しない時に
	 * コード順送りボタンをクリックした場合も同様にエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_SEARCH					= "PF1552";
	
	/**
	 * 新規登録コマンド。<br>
	 */
	public static final String	CMD_INSERT					= "PF1555";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄の入力内容を基に人事汎用通常情報テーブルの登録・更新を行う。<br>
	 * レコードが存在しなければ新規登録し、既存のものがあるなら更新の処理を行う。<br>
	 * 入力チェックを行った際に入力必須項目が入力されていない場合はエラーメッセージにて通知。<br>
	 */
	public static final String	CMD_UPDATE					= "PF1556";
	
	/**
	 * 削除コマンド。<br>
	 * <br>
	 * 登録済みの情報を削除する。<br>
	 */
	public static final String	CMD_DELETE					= "PF1557";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER				= "PF1559";
	
	/**
	 * 人事汎用管理表示区分(通常)。<br>
	 */
	public static final String	KEY_VIEW_BINARY_NORMAL_CARD	= "BinaryNormal";
	
	/**
	 * パラメータID(バイナリファイル)。
	 */
	public static final String	PRM_FILE_BINARY_NORMAL		= "fileBinaryNormal";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public HumanBinaryNormalCardAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択
			prepareVo(true, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_SEARCH)) {
			// 検索
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_INSERT)) {
			// 新規登録
			prepareVo();
			insert();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			update();
		} else if (mospParams.getCommand().equals(CMD_DELETE)) {
			// 削除
			prepareVo();
			delete();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new HumanBinaryNormalCardVo();
	}
	
	/**
	 * 編集画面選択表示処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void select() throws MospException {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 人事汎用管理区分設定
		vo.setDivision(getTransferredType());
		// パンくず名準備
		StringBuilder name = new StringBuilder(NameUtility.getName(mospParams, vo.getDivision()));
		name.append(PfNameUtility.information(mospParams));
		// パンくず名設定
		TopicPathUtility.setTopicPathName(mospParams, vo.getClassName(), name.toString());
		// 人事管理共通情報利用設定
		setPlatformHumanSettings(CMD_SEARCH, PlatformHumanConst.MODE_HUMAN_NO_ACTIVATE_DATE);
		// 人事管理共通情報設定
		setTargetHumanCommonInfo();
		// 初期値設定
		setDefaultValues();
		// 人事汎用バイナリ通常情報設定
		setBinaryNormalInfo();
	}
	
	/**
	 * 検索処理を行う。<br>
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void search() throws MospException {
		// 人事管理共通情報の検索
		searchHumanCommonInfo();
		// 項目初期化
		setDefaultValues();
		// 人事汎用通常情報設定
		setBinaryNormalInfo();
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void insert() throws MospException {
		// 登録クラス取得
		HumanBinaryNormalRegistBeanInterface regist = platform().humanBinaryNormalRegist();
		// DTOの準備
		HumanBinaryNormalDtoInterface dto = regist.getInitDto();
		// DTOに値を設定
		setDtoFields(dto);
		// 更新処理
		regist.insert(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 人事汎用通常情報設定
		setBinaryNormalInfo();
	}
	
	/**
	 * 更新処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 参照クラス取得
		HumanBinaryNormalReferenceBeanInterface reference = reference().humanBinaryNormal();
		// 参照情報
		HumanBinaryNormalDtoInterface dto = reference.findForKey(vo.getHidRecordId(), false);
		dto.setFileName(vo.getFileBinaryNormal());
		dto.setFileRemark(vo.getTxtFileRemark());
		// 更新
		platform().humanBinaryNormalRegist().update(dto);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 登録失敗メッセージを設定
			PfMessageUtility.addMessageInsertFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageInsertSucceed(mospParams);
		// 人事汎用通常情報設定
		setBinaryNormalInfo();
	}
	
	/**
	 * DTOに値を設定する。
	 * @param dto 人事バイナリ通常情報
	 * @throws MospException 例外処理が発生した場合
	 */
	private void setDtoFields(HumanBinaryNormalDtoInterface dto) throws MospException {
		// ファイルをbyte[]で取得
		byte[] file = BinaryUtility.getBinaryData(mospParams.getRequestFile(PRM_FILE_BINARY_NORMAL));
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// DTOに設定
		dto.setPersonalId(vo.getPersonalId());
		dto.setHumanItemType(vo.getDivision());
		dto.setHumanItemBinary(file);
		dto.setFileType(HumanUtility.getBinaryFileType(vo.getFileBinaryNormal()));
		dto.setFileName(vo.getFileBinaryNormal());
		dto.setFileRemark(vo.getTxtFileRemark());
	}
	
	/**
	 * 削除処理を行う。<br>
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void delete() throws MospException {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 登録クラス取得
		HumanBinaryNormalRegistBeanInterface regist = platform().humanBinaryNormalRegist();
		// 削除処理
		regist.delete(reference().humanBinaryNormal().findForKey(vo.getHidRecordId(), false));
		// 削除結果確認
		if (mospParams.hasErrorMessage()) {
			// 削除失敗メッセージを設定
			PfMessageUtility.addMessageDeleteFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 削除成功メッセージを設定
		PfMessageUtility.addMessageDeleteSucceed(mospParams);
		// 初期値設定
		setDefaultValues();
		// モード設定(新規登録)
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 譲渡Actionクラス名取得
		String actionName = getTransferredAction();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 譲渡Actionクラス名毎に処理
		if (actionName.equals(HumanInfoAction.class.getName())) {
			// 社員の人事情報をまとめて表示
			mospParams.setNextCommand(HumanInfoAction.CMD_SELECT);
		}
	}
	
	/**
	 * VOに初期値を設定する。<br>
	 */
	protected void setDefaultValues() {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 初期値設定
		vo.setPltFileType("");
		vo.setFileBinaryNormal("");
		vo.setTxtFileRemark("");
		// 人事汎用管理区分参照権限設定
		vo.setJsIsReferenceDivision(RoleUtility.getReferenceDivisionsList(mospParams).contains(getTransferredType()));
		// プルダウン設定
		setPulldown();
	}
	
	/**
	 * 人事汎用通常情報を設定する。
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void setBinaryNormalInfo() throws MospException {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// 人事汎用管理区分取得
		String division = vo.getDivision();
		// 人事汎用通常情報参照クラス取得
		HumanBinaryNormalReferenceBeanInterface binaryNormalRefer = reference().humanBinaryNormal();
		// 登録情報を取得しVOに設定
		HumanBinaryNormalDtoInterface dto = binaryNormalRefer.findForInfo(vo.getPersonalId(), division);
		// 情報がない場合
		if (dto == null) {
			// 初期値設定
			setDefaultValues();
			vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_INSERT);
			return;
		}
		// 画面に設定
		setVoFields(dto);
		vo.setModeCardEdit(PlatformConst.MODE_CARD_EDIT_EDIT);
	}
	
	/**
	 * DTOの値をVO(編集項目)に設定する。<br>
	 * @param dto 対象DTO
	 */
	protected void setVoFields(HumanBinaryNormalDtoInterface dto) {
		// バイナリ通常情報がない場合
		if (dto == null) {
			// 初期化
			setDefaultValues();
			return;
		}
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		vo.setHidRecordId(dto.getPfaHumanBinaryNormalId());
		vo.setPltFileType(dto.getFileType());
		vo.setFileBinaryNormal(dto.getFileName());
		vo.setTxtFileRemark(dto.getFileRemark());
	}
	
	/**
	 * プルダウンを設定する。
	 */
	protected void setPulldown() {
		// VO取得
		HumanBinaryNormalCardVo vo = (HumanBinaryNormalCardVo)mospParams.getVo();
		// プルダウン設定
		MospProperties properties = mospParams.getProperties();
		String[][] aryBinaryFileType = properties.getCodeArray(PlatformConst.CODE_KEY_BINARY_FILE_TYPE, true);
		vo.setAryPltFileType(aryBinaryFileType);
	}
}
