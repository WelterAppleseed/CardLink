package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.FirstTimeOnFragmentManager
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import com.example.cardlinker.domain.usecases.base.SuspendReturnUseCase
import javax.inject.Inject

class CheckFirstTimeOnFragmentUseCase @Inject constructor(private val firstTimeOnFragmentRepository: FirstTimeOnFragmentManager) :
    BaseReturnUseCase<Boolean> {
    private var fragmentName = ""
    fun updateState() {
        firstTimeOnFragmentRepository.updateFirstTimeOnFragment(fragmentName)
    }
    fun saveInput(fragmentName: String) = run {
        this.fragmentName = fragmentName
    }
    override fun execute(): Boolean {
        return firstTimeOnFragmentRepository.getFirstTimeOnFragment(fragmentName)
    }

}