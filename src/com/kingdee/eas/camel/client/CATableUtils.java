package com.kingdee.eas.camel.client;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;

import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.camel.CAArrayUtils;
import com.kingdee.eas.camel.CANumberHelper;
import com.kingdee.eas.camel.constant.CANumberConstant;
import com.kingdee.eas.common.SysConstant;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.util.enums.IntEnum;
import com.kingdee.util.enums.StringEnum;

/**
 * 
 * COSCO table ������ <br/>
 * note: �����������ӷ���˵��
 * @author jason
 *
 */
public final class CATableUtils {
	/**
	 * ������ ����Ƿ�ѡ����
	 * @param comp
	 * @param tb
	 */
	public static void checkSelectedRow(Component comp, KDTable tb) {
		if(tb.getRowCount() == 0 || tb.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
            SysUtil.abort();
        }
	}
	
	/**
	 * ������ ����table���Ҽ��ĵ����˵�
	 * @param ui
	 * @param table
	 */
	public static void disableExport(CoreUI ui, KDTable table)
    {
        String menuItemExportExcel = "menuItemExportExcel";
        String menuItemExportSelected = "menuItemExportSelected";
        String menuMail = "menuMail";
        String menuPublishReport = "menuPublishReport";
        if(table == null || ui == null)
            return;
        KDTMenuManager tm = ui.getMenuManager(table);
        if(tm != null)
        {
            java.awt.Component menus[] = tm.getMenu().getComponents();
            for(int i = 0; i < menus.length; i++)
            {
                if(!(menus[i] instanceof JMenuItem))
                    continue;
                JMenuItem menu = (JMenuItem)menus[i];
                if(menu != null && menu.getName() != null && (menu.getName().equalsIgnoreCase(menuItemExportExcel) || menu.getName().equalsIgnoreCase(menuItemExportSelected) || menu.getName().equalsIgnoreCase(menuMail) || menu.getName().equalsIgnoreCase(menuPublishReport)))
                    menu.setVisible(false);
                else
                if(menu == null);
            }
        }
    }
	
	/**
	 * ������ ����table��enter ������
	 * @param table
	 */
	public static void enableEnterInTable(KDTable table)
    {
        KDTableHelper.setEnterKeyJumpOrientation(table, 0);
    }

    public static void enableAutoAddLine(KDTable table)
    {
        KDTableHelper.updateEnterWithTab(table, true);
    }

    public static void disableAutoAddLine(KDTable table)
    {
        KDTableHelper.updateEnterWithTab(table, false);
    }

    public static void enableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, true, null);
    }

    public static void disableAutoAddLineDownArrow(KDTable table)
    {
        KDTableHelper.downArrowAutoAddRow(table, false, null);
    }

    /**
     * ������ �Ƴ�table action<br/>
     * <ul>
     * 		<li>Cut
     * 		<li>Delete
     * 		<li>Paste
     * </ul>
     * @param table
     */
    public static void disableDelete(KDTable table)
    {
        ActionMap actionMap = table.getActionMap();
        actionMap.remove("Cut");
        actionMap.remove("Delete");
        actionMap.remove("Paste");
    }
    
    /**
     * ������ ����table��ĳһ�м���
     * @param tb
     * @param colIndex
     * @param lock
     * 		true if is lock, otherwise unlock
     */
    public static void setColumnLock(KDTable tb, int colIndex, boolean lock) {
    	IColumn column = tb.getColumn(colIndex);
    	setColumnLock(column, lock);
    }
    
    /**
     * ������ ����table��ĳһ�м���
     * @param tb
     * @param colIndex
     * @param lock
     * 		true if is lock, otherwise unlock
     */
    public static void setColumnLock(KDTable tb, String key, boolean lock) {
    	IColumn column = tb.getColumn(key);
    	setColumnLock(column, lock);
    }
    
    /**
     * <p>�����������Ƿ�����.</p>
     * 
     * @param tb
     * @param keys ��������.
     * @param lock true:����,false:������
     */
    public static void setColumnLockOrUnlock(KDTable tb, String[] keys, boolean lock) {
    	if (CAArrayUtils.isEmpty(keys)) {
    		return;
    	}
    	
    	for (int i = 0; i < keys.length; i++) {
    		setColumnLock(tb, keys[i], lock);
    	}
    }
    
    /**
     * ������ ����table��ĳһ�м���
     * @param tb
     * @param colIndex
     * @param lock
     * 		true if is lock, otherwise unlock
     */
    public static void setColumnLock(IColumn column, boolean lock) {
    	if (null != column) {
    		column.getStyleAttributes().setLocked(lock);
    	}
    }
    
    /**
     * ��������������������.
     * 
     * @param tbl
     * @param keys  ������
     * @param isHided  �Ƿ�����
     */
    public static void setColumnHided(KDTable tbl, String[] keys, boolean isHided) {
    	if (CAArrayUtils.isEmpty(keys)) {
    		return;
    	}
    	for (int i = 0; i < keys.length; i++) {
    		setColumnHided(tbl, keys[i], isHided);
    	}
    }
    
    /**
     * ����������������.
     * 
     * @param tbl
     * @param key  ������
     * @param isHided  �Ƿ�����
     */
    public static void setColumnHided(KDTable tbl, String key, boolean isHided) {
    	IColumn column = tbl.getColumn(key);
    	setColumnHided(column, isHided);
    }
    
    /**
     * ����������������.
     * 
     * @param column
     * @param isHided
     */
    public static void setColumnHided(IColumn column, boolean isHided) {
    	if (null != column) {
    		column.getStyleAttributes().setHided(isHided);
    	}
    }
    
    /**
     * �����б�¼
     * @param tbl
     * @param keys
     * @param required true is required, otherwise false.
     */
    public static void setColumnRequired(KDTable tbl, String[] keys, boolean required) {
    	if (CAArrayUtils.isEmpty(keys)) {
    		return;
    	}
    	for (int i = 0; i < keys.length; i++) {
    		setColumnRequired(tbl, keys[i], required);
    	}
    }
    
    /**
     * �����б�¼
     * @param tbl
     * @param key the name of the column
     * @param required true is required, otherwise false.
     */
    public static void setColumnRequired(KDTable tbl, String key, boolean required) {
    	setColumnRequired(tbl.getColumn(key), required);
    }
    
    /**
     * �������Ƿ��¼
     * @param column
     * @param required true����¼������Ϊ�Ǳ�¼
     */
    public static void setColumnRequired(IColumn column, boolean required) {
    	if (null != column) {
    		column.setRequired(required);
    	}
    }
    
    /**
     * ������ ���õ�Ԫ�����
     * @param cell
     * 		��Ԫ��
     * @param value
     * 		ֵ
     */
    public static void setCellValue(ICell cell, Object value) {
    	if (null != cell) {
    		cell.setValue(value);
    	}
    }
    
    /**
     * ������ ����table��ĳһ�м���
     * @param tb
     * @param colIndex
     * @param lock
     * 		true if is lock, otherwise unlock
     */
    public static void setRowLock(KDTable tb, int rowIndex, boolean lock) {
    	IRow row = tb.getRow(rowIndex);
    	if (null != row) {
    		row.getStyleAttributes().setLocked(lock);
    	}
    }
    
    
	/**
	 * 
	 * ��ȡ����ѡ�е��У� ��ѡ
	 * 
	 * @param table
	 *            KDTable
	 * 
	 * @return int[]
	 * 
	 */

	public static int[] getSelectedRows(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return new int[0];
		} else {
			TreeSet set = new TreeSet();

			for (int i = 0; i < table.getSelectManager().size(); i++) {
				IBlock block = KDTBlock.change(table, table.getSelectManager()
						.get(i));
				for (int j = block.getTop(); j <= block.getBottom(); j++) {
					set.add(new Integer(j));
				}
			}

			int[] rows = new int[set.size()];
			Iterator iter = set.iterator();
			int k = 0;
			while (iter.hasNext()) {
				rows[k] = ((Integer) iter.next()).intValue();
				k++;
			}
			return rows;
		}
	}
	
	/**
	 * ��ȡָ�������ݼ���.
	 * 
	 * @param table
	 * @param key
	 * @return
	 */
	public static List getSelectedRowValues(KDTable table, String key) {
		int[] rows = getSelectedRows(table);
		if (null == rows || 0 == rows.length || null == table.getColumn(key)) {
			return null;
		}
		
		IRow row = null;
		List idList = new ArrayList(rows.length);
		
		for (int i = 0; i < rows.length; i++) {
			row = table.getRow(rows[i]);
			idList.add(row.getCell(key).getValue());
		}
		
		return idList;
	}
	
	/**
	 * 
	 * ��ȡ����ѡ�е��У� ��ѡ
	 * 
	 * @param table
	 *            KDTable
	 * 
	 * @return int[]
	 * 
	 */

	public static int[] getSelectedColmns(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return new int[0];
		} else {
			TreeSet set = new TreeSet();
			for (int i = 0; i < table.getSelectManager().size(); i++) {
				IBlock block = KDTBlock.change(table, table.getSelectManager()
						.get(i));
				for (int j = block.getLeft(); j <= block.getRight(); j++) {
					set.add(new Integer(j));
				}
			}

			int[] rows = new int[set.size()];
			Iterator iter = set.iterator();
			int k = 0;
			while (iter.hasNext()) {
				rows[k] = ((Integer) iter.next()).intValue();
				k++;
			}
			return rows;
		}
	}

	/**
	 * ��ȡѡ�е�����
	 * 
	 * @param table
	 *            KDTable
	 * @return int
	 */
	public static int getSelectedRowCount(KDTable table) {
		int[] rows = getSelectedRows(table);
		return rows.length;
	}
	
	/**
	 * ָ�����кϼ�.
	 * 
	 * @param table  ������
	 * @param key  ������
	 * @return
	 */
	public static BigDecimal calculateColumnTotal(KDTable table, String key) {
		if (null == table.getColumn(key)) {
			return CANumberConstant.ZERO;
		}
		IRow row = null;
		BigDecimal result = null;
		
		for (int i = 0; i < table.getRowCount(); i++) {
			row = table.getRow(i);
			result = CANumberHelper.add(result, row.getCell(key).getValue());
		}
		
		return result;
	}
	
	/**
	 * �����ƶ���.
	 * 
	 * @param table
	 * @param fromList
	 * @param toList
	 */
	public static void moveRows(KDTable table, List fromList, List toList) {
		if (null == fromList || fromList.isEmpty()
				|| null == toList || toList.isEmpty()) {
			return;
		}
		int topIndex = UIRuleUtil.getIntValue(toList.get(toList.size() - 1));
		int startIndex = UIRuleUtil.getIntValue(fromList.get(0));
		
		for (int i = 0, size = fromList.size(); i < size; i++) {
			table.moveRow(startIndex, topIndex);
		}
	}

	public static int[] getSelectedTopBlock(KDTable table) {
		if (table.getSelectManager().size() == 0) {
			return new int[0];
		} else {
			IBlock block = null;
			IBlock temp = null;
			for (int i = 0; i < table.getSelectManager().size(); i++) {
				temp = KDTBlock.change(table, table.getSelectManager().get(i));
				if (block == null) {
					block = temp;
				} else {
					if (temp.getTop() < block.getTop()) {
						block = temp;
					}
				}
			}

			int top = block.getTop();
			int button = block.getBottom();
			int[] rows = new int[button - top + 1];

			for (int j = block.getTop(); j <= block.getBottom(); j++) {
				rows[j - top] = j;
			}

			Arrays.sort(rows);

			return rows;
		}
	}
	
	/**
	 * ��ȡ�������
	 * ע�⣺������Ϊ��ģʽ�ͻ�ȡ��Ӧ������
	 * @param table
	 * @return
	 */
	public static int getVirtualRowCount(KDTable table) {
		if (KDTDataRequestManager.VIRTUAL_MODE_PAGE == table.getDataRequestManager().getDataRequestMode()) {
			return table.getBody().size();
		}
		return table.getRowCount();
	}
	
	/**
	 * ׷��Ĭ���кϼ���
	 * ע�⣺
	 * 	�̳�editui�Ͳ�����ʹ������������������ṩ��Ӧ��api
	 * @param tbl
	 * @param fieldSumList
	 */
	public static void appendFootRow(KDTable tbl, String fieldSumList[]) {
		IRow footRow = null;
		KDTFootManager footRowManager = tbl.getFootManager();
		if(footRowManager == null) {
			String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(tbl);
            footRowManager.addFootView();
            tbl.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
            tbl.getIndexColumn().setWidthAdjustMode((short)1);
            tbl.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
		} else {
            footRow = footRowManager.getFootRow(0);
        }
		
		String colFormat = "%{#,##0.##########}f";
        int columnCount = tbl.getColumnCount();
        for(int c = 0; c < columnCount; c++){
            String fieldName = tbl.getColumn(c).getKey();
            for(int i = 0; i < fieldSumList.length; i++) {
                String name = fieldSumList[i];
                if(name.equalsIgnoreCase(fieldName)) {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat(colFormat);
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.RIGHT);
                    cell.getStyleAttributes().setFontColor(Color.BLACK);
                    cell.setValue(SysConstant.BIGZERO);
                }
            }
        }
        
        for (int i = 0; i < fieldSumList.length; i++) {
        	setCellValue(footRow.getCell(fieldSumList[i]), calculateColumnTotal(tbl, fieldSumList[i]));
        }

        footRow.getStyleAttributes().setBackground(new Color(246, 246, 191));
	}
    
    /**
     * ������ �õ�table clone �ĸ�����
     * @return
     */
    public static TableClone getTableClone() {
    	return tClone;
    }
    
    /**
     * table ���ݵĸ�����
     * @author jason
     *
     */
    public static class TableClone {
    	public TableClone() {
    		
    	}
    	
    	/**
    	 * ����������table
    	 * @param source
    	 * @param dest
    	 */
    	public static void fillToTable(KDTable source, KDTable dest) {
    		if (null == source || null == dest) {
    			return;
    		}
    		
    		dest.setFormatXml(source.getFormatXml());

    		dest.checkParsed(true);
    		
    		copyColumns(source, dest);
    		
    		copyRows(source, dest);
    	}
    	
    	/**
    	 * copy��
    	 */
    	public static void copyRows(KDTable source, KDTable dest) {
    		int count = source.getRowCount();
    		dest.addRows(count);
    		
    		int length = source.getRowCount();
    		
    		IRow row = null;
    		for (int i = 0; i < count; i++) {
    			row = dest.getRow(i);
    			row.setUserObject(source.getRow(i).getUserObject());
    			copyCellValue(source, dest, i);
    		}
    	}
    	
    	public static void copyCellValue(KDTable source, KDTable dest, int rowIndex) {
    		int count = source.getColumnCount();
    		
    		IColumn column = null;
    		ICell cell = null;
    		for (int i = 0; i < count; i++) {
    			column = source.getColumn(i);
    			
    			if (null == column) {
    				continue;
    			}
    			
    			Object obj = null;
    			cell = source.getCell(rowIndex, column.getKey());
    			
    			if (null != cell) {
    				obj = cell.getValue();
    			}
    			
    			setCellValue(dest, rowIndex, column.getKey(), obj);
    		}
    	}
    	
    	public static void setCellValue(KDTable tb, int rowIndex, String key, Object obj) {
    		ICell cell = tb.getCell(rowIndex, key);
    		if (null != cell) {
    			Class clazz = null;
    			Object value = null;
    			if (null != tb.getColumn(key)) {
    				if (null != tb.getColumn(key).getEditor()) {
    					if (null != tb.getColumn(key).getEditor().getValue()) {
    						value = tb.getColumn(key).getEditor().getValue();
    					}
    				}
    			}
    			
    			if (null != value) {
    				clazz = value.getClass();
    			}
    			
    			if (null != clazz) {
    				if (StringEnum.class.isAssignableFrom(clazz)) {
    					cell.setValue(EnumUtils.getEnumByValue(clazz, (String)obj));
    				} else if (IntEnum.class.isAssignableFrom(clazz)) {
    					cell.setValue(EnumUtils.getEnumByValue(clazz, (String)obj));
    				} else {
    					cell.setValue(obj);
    				}
    			} else {
    				cell.setValue(obj);
    			}
    		}
    	}
    	
    	/**
    	 * ������ ������
    	 * @param source
    	 * 		ԭtable
    	 * @param dest
    	 * 		��¡���table
    	 */
    	public static void copyColumns(KDTable source, KDTable dest) {
    		int count = source.getColumnCount();
    		IColumn column = null;
    		for (int colIndex = 0; colIndex < count; colIndex++) {
    			column = source.getColumn(colIndex);
    			if (null == column) 
    				continue;
    			IColumn destCol = dest.getColumn(column.getKey());
    			if (null == destCol)
    				continue;
    			destCol.setEditor(column.getEditor());
    		}
    	}
    }
    
    private static TableClone tClone;
    
    static {
    	tClone = new TableClone();
    }
}
