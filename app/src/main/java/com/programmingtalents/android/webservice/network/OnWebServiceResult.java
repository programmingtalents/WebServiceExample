package com.programmingtalents.android.webservice.network;


import com.programmingtalents.android.webservice.utils.CommonUtilities;

/**
 * @author Amit
 *
 */
public interface OnWebServiceResult {
	public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type);
}
