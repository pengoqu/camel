package com.kingdee.eas.camel.client;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

import com.kingdee.bos.appframework.databinding.ComponentProperty;
import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.appframework.databinding.DataComponentMap;
import com.kingdee.bos.appframework.databinding.Field;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.camel.CAArrayUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.EditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 客户端效验帮助类.
 * 
 */
public final class CAVerifyUtils {
	
	public static void checkSelected(CoreUI ui, KDTable table) {
		if(table.getRowCount() == 0 || table.getSelectManager().size() == 0) {
            MsgBox.showWarning(ui, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
            SysUtil.abort();
        }
	}
	
	 /**
     * 必录项校验
     * @param editUI
     */
    public static void verifyRequired(EditUI editUI) throws Exception {
    	if(editUI == null) return;
    	verifyRequired(editUI.getDataBinder());
    }

    /**
     * 必录项校验
     * @param dataBinder
     */
    public static void verifyRequired(DataBinder dataBinder) throws Exception {
    	if(dataBinder == null) return;
    	verifyHead(dataBinder);
    }
    
    /**
     * 校验单据头控件
     * @param dataBinder
     * @throws Exception
     */
    public static void verifyHead(DataBinder dataBinder) throws Exception {
    	DataComponentMap componentMap = dataBinder.getDataComponentMap();
        ArrayList headerFields = componentMap.getHeaderFields();
       // headerFields.set(0, "测试05");
        //System.out.println("进入测试");
        if(headerFields != null && headerFields.size() > 0)
        {
        	for(int i=0; i<headerFields.size(); i++) {
        		Field field = (Field)headerFields.get(i);
        		ComponentProperty compProp = componentMap.getComponentProperty(field);
        		Component comp = compProp.getComponent();
        		verifyEditor(comp, null);
        	}
        }
	}

    /**
     * 校验编辑器控件
     * @param comp
     * @throws Exception
     */
    public static void verifyEditor(Component comp, String compText) throws Exception {
    	if(StringUtils.isEmpty(compText)) {
    		compText = getCompLabelText(comp);
    	}
    	if (comp instanceof KDTextField) {
			KDTextField txtField = (KDTextField)comp;
			if (txtField.isRequired() && UIRuleUtil.isNull(txtField.getText())) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDTextArea) {
			KDTextArea area = (KDTextArea)comp;
			if (area.isRequired() && UIRuleUtil.isNull(area.getText())) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDComboBox) {
			KDComboBox combo = (KDComboBox)comp;
			if (combo.isRequired() && UIRuleUtil.isNull(combo.getSelectedItem())) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDDatePicker) {
			KDDatePicker pkDate = (KDDatePicker)comp;
			if (pkDate.isRequired() && UIRuleUtil.isNull(pkDate.getValue())) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDFormattedTextField) {
			KDFormattedTextField txtFormat = (KDFormattedTextField)comp;
//			if (txtFormat.isRequired() && UIRuleUtil.isNull(txtFormat.getBigDecimalValue())) {
			// 金额允许为0，不使用UIRuleUtil工具类
			if (txtFormat.isRequired() && txtFormat.getBigDecimalValue() == null) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDSpinner) {
			KDSpinner spin = (KDSpinner)comp;
			if (spin.isRequired() && spin.getBigDecimalValue() == null) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		} else if (comp instanceof KDBizPromptBox) {
			KDBizPromptBox promtBox = (KDBizPromptBox)comp;
			if (promtBox.isRequired() && promtBox.getData() == null) {
				throw new EASBizException(EASBizException.CHECKBLANK,new Object[] {compText});
			}
		}
	}
	
	public static String getCompLabelText(Component component) {
		String text = "";
		if (component.getParent() instanceof KDLabelContainer) {
			text = ((KDLabelContainer)component.getParent()).getBoundLabelText();
		} else if (component instanceof KDTextArea) {
			Container cont = component.getParent();
			if (cont != null){
				Container cont2 = cont.getParent();
				
				if ((cont2 instanceof KDScrollPane) && 
						(cont2.getParent() instanceof KDLabelContainer)) {
					text = ((KDLabelContainer)cont2.getParent()).getBoundLabelText();
				}
			}
		}
		
		return text;
    }
	
	/**
	 * <p>效验列所有的数据不能为空.</p>
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param keys
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String[] keys) {
		if (CAArrayUtils.isEmpty(keys)) {
			return;
		}
		for (int i = 0; i < keys.length; i++) {
			verifyInput(ui, kdtEntries, keys[i]);
		}
	}
	
	/**
	 * <p>效验列所有的数据不能为空.</p>
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key 列名称
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); ++j) {
			row = kdtEntries.getRow(j);
			verifyInput(ui, kdtEntries, row, key);
		}
	}
	
	/**
	 * <p>效验指定行对应列是否为空.</p>
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param row 行对象
	 * @param key 列名称
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, IRow row, String key) {
		int colIndex = kdtEntries.getColumnIndex(key);
	
		if (!(CAClientUtil.isEmpty(row.getCell(key).getValue())))
			return;
		kdtEntries.getEditManager().editCellAt(row.getRowIndex(), colIndex);
	
		String headValue = (String)kdtEntries.getHeadRow(0).getCell(key).getValue();
		String msg = headValue + " " + EASResource.getString(path, "CanNotBeNull");
		msg = msg.replaceAll("#", " " + headValue + " ");
		MsgBox.showWarning(ui, msg);
	
		SysUtil.abort();
	}
	
	/**
	 * 效验分录不能为空.
	 * 
	 * @param ui
	 * @param kdtEntries
	 */
	public static void verifyEmpty(CoreUIObject ui, KDTable kdtEntries) {
		if ((kdtEntries == null) || (kdtEntries.getRowCount() < 1)) {
			MsgBox.showWarning(ui, EASResource.getString(path, "EntryCanNotBeNull"));
			SysUtil.abort();
		}
	}
	
	private static String path = "com.kingdee.eas.fm.common.FMResource";
}
