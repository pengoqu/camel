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
 * �ͻ���Ч�������.
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
     * ��¼��У��
     * @param editUI
     */
    public static void verifyRequired(EditUI editUI) throws Exception {
    	if(editUI == null) return;
    	verifyRequired(editUI.getDataBinder());
    }

    /**
     * ��¼��У��
     * @param dataBinder
     */
    public static void verifyRequired(DataBinder dataBinder) throws Exception {
    	if(dataBinder == null) return;
    	verifyHead(dataBinder);
    }
    
    /**
     * У�鵥��ͷ�ؼ�
     * @param dataBinder
     * @throws Exception
     */
    public static void verifyHead(DataBinder dataBinder) throws Exception {
    	DataComponentMap componentMap = dataBinder.getDataComponentMap();
        ArrayList headerFields = componentMap.getHeaderFields();
       // headerFields.set(0, "����05");
        //System.out.println("�������");
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
     * У��༭���ؼ�
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
			// �������Ϊ0����ʹ��UIRuleUtil������
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
	 * <p>Ч�������е����ݲ���Ϊ��.</p>
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
	 * <p>Ч�������е����ݲ���Ϊ��.</p>
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param key ������
	 */
	public static void verifyInput(CoreUIObject ui, KDTable kdtEntries, String key) {
		IRow row = null;
		for (int j = 0; j < kdtEntries.getRowCount(); ++j) {
			row = kdtEntries.getRow(j);
			verifyInput(ui, kdtEntries, row, key);
		}
	}
	
	/**
	 * <p>Ч��ָ���ж�Ӧ���Ƿ�Ϊ��.</p>
	 * 
	 * @param ui
	 * @param kdtEntries
	 * @param row �ж���
	 * @param key ������
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
	 * Ч���¼����Ϊ��.
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
