package name.mharbovskyi.findchargingstation.presentation

interface Router {
    fun showAuthentication()
    fun hideAuthentication()
    fun showChargePoints()
    fun showGreeting(user: ViewUser)
}