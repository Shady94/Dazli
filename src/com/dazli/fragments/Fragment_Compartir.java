package com.dazli.fragments;

import com.dazli.fin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class Fragment_Compartir extends Fragment implements OnClickListener {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View RootView = inflater.inflate(R.layout.fragment_compartir,
				container, false);

		inicializarcomponentes(RootView);
		return RootView;
	}

	private void inicializarcomponentes(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
