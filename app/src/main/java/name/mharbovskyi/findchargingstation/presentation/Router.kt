package name.mharbovskyi.findchargingstation.presentation

interface Router {
    fun showAuthentication()
    fun showChargePoints()
    fun showGreeting(user: ViewUser)
}