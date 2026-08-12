/*
 * Copyright (c) 2020, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this
 *    list of conditions and the following disclaimer in the documentation and/or
 *    other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package no.nordicsemi.android.log.example;

import java.util.Calendar;

import no.nordicsemi.android.log.LogContract.Log.Level;
import android.content.Context;
import android.database.Cursor;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LogAdapter extends CursorAdapter {
	// Maps each log level to a color resource. The colors are theme-aware
	// (see res/values/colors.xml and res/values-night/colors.xml) and are
	// resolved against the current configuration in bindView().
	private static final SparseIntArray mColorRes = new SparseIntArray();

	static {
		mColorRes.put(Level.DEBUG, R.color.log_level_debug);
		mColorRes.put(Level.VERBOSE, R.color.log_level_verbose);
		mColorRes.put(Level.INFO, R.color.log_level_info);
		mColorRes.put(Level.APPLICATION, R.color.log_level_application);
		mColorRes.put(Level.WARNING, R.color.log_level_warning);
		mColorRes.put(Level.ERROR, R.color.log_level_error);
	}

	public LogAdapter(@NonNull final Context context) {
		super(context, null, 0);
	}

	@Override
	public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
		final View view = LayoutInflater.from(context).inflate(R.layout.log_item, parent, false);

		final ViewHolder holder = new ViewHolder();
		holder.time = view.findViewById(R.id.time);
		holder.data = view.findViewById(R.id.data);
		view.setTag(holder);
		return view;
	}

	@Override
	public void bindView(final View view, final Context context, final Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(cursor.getLong(1 /* TIME */));
		holder.time.setText(context.getString(R.string.log, calendar));

		final int level = cursor.getInt(2 /* LEVEL */);
		holder.data.setText(cursor.getString(3 /* DATA */));
		final int colorRes = mColorRes.get(level, R.color.log_level_info);
		holder.data.setTextColor(ContextCompat.getColor(context, colorRes));
	}

	@Override
	public boolean isEnabled(final int position) {
		return false;
	}

	private static class ViewHolder {
		private TextView time;
		private TextView data;
	}

}
