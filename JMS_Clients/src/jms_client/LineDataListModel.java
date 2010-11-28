package jms_client;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Class to store {@link LineData} and notify registered listener
 * about data changes
 * (model in MVC concept) 
 * 
 * @author Florian Mueller
 */
public class LineDataListModel{
	public static interface ILineDataListener{
		public void onDataChanged(LineData lineData);
	}

	private LinkedList<ILineDataListener> listenerList = null;
	private Vector<LineData> lineDataVector = null;
	
	/**
	 * Default constructor
	 */
	public LineDataListModel() {
		listenerList = new LinkedList<LineDataListModel.ILineDataListener>();
		lineDataVector = new Vector<LineData>();
	}
	
	/**
	 * Register a new listener to this model
	 * 
	 * @param listener
	 */
	public void registerListener(ILineDataListener listener){
		if(!listenerList.contains(listener))
			listenerList.add(listener);
	}
	
	/**
	 * remove a listener from this model
	 * 
	 * @param listener
	 */
	public void unregisterListener(ILineDataListener listener){
		if(listenerList.contains(listener))
			listenerList.remove(listener);
	}
	
	/**
	 * Add a new element to the model
	 * 
	 * @param lineData
	 */
	public void addElement(LineData lineData){
		lineDataVector.add(lineData);
		notifyDataChanged(lineData);
	}
	
	/**
	 * Get all elements from this model
	 * 
	 * @return unmodifiable collection of {@link LineData}
	 */
	public Collection<LineData> getAllElements(){
		return Collections.unmodifiableCollection(lineDataVector);
	}
	
	/**
	 * Clears the mode
	 */
	public void removeAll(){
		lineDataVector.clear();
	}
	
	// notify registered listener if data has changed
	private void notifyDataChanged(LineData lineData){
		for (ILineDataListener listener : listenerList)
			listener.onDataChanged(lineData);
	}
}
