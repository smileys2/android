/**
 * ownCloud Android client application
 *
 * @author Juan Carlos Garrote Gascón
 *
 * Copyright (C) 2021 ownCloud GmbH.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.owncloud.android.presentation.viewmodels.security

import androidx.lifecycle.ViewModel
import com.owncloud.android.data.preferences.datasources.SharedPreferencesProvider
import com.owncloud.android.presentation.ui.security.PassCodeActivity

class PassCodeViewModel(
    private val preferencesProvider: SharedPreferencesProvider
) : ViewModel() {

    fun setPassCode(passcode: String) {
        preferencesProvider.putString(PassCodeActivity.PREFERENCE_PASSCODE, passcode)
        preferencesProvider.putBoolean(PassCodeActivity.PREFERENCE_SET_PASSCODE, true)
    }

    fun removePassCode() {
        preferencesProvider.removePreference(PassCodeActivity.PREFERENCE_PASSCODE)
        preferencesProvider.putBoolean(PassCodeActivity.PREFERENCE_SET_PASSCODE, false)
    }

    fun checkPassCodeIsValid(passCodeDigits: Array<String?>): Boolean {
        val passCodeString = preferencesProvider.getString(PassCodeActivity.PREFERENCE_PASSCODE, loadPinFromOldFormatIfPossible())
        if (passCodeString.isNullOrEmpty()) return false
        var isValid = true
        var i = 0
        while (i < passCodeDigits.size && isValid) {
            val originalDigit = passCodeString[i].toString()
            isValid = passCodeDigits[i] != null && passCodeDigits[i] == originalDigit
            i++
        }
        return isValid
    }

    private fun loadPinFromOldFormatIfPossible(): String {
        var pinString = ""
        for (i in 1..4)
            pinString += preferencesProvider.getString(PassCodeActivity.PREFERENCE_PASSCODE_D + i, null)

        return pinString
    }
}
