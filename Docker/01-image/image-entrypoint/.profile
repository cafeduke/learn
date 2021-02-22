echo "Sourcing $HOME/.profile"

if [[ "${USER}" == "root" ]]
then
  prompt_sign="#"
else
  prompt_sign=">"
fi

# Environment Variables
# ---------------------
export PS1='\[\e[0;92;1m\]`hostname -i`\[\e[m\] \[\e[0;94;1m\]`pwd`\[\e[0;92;1m\]$prompt_sign\[\e[m\] '
export LS_COLORS=$LS_COLORS:'ow=1;34:tw=1;34'

# Alias
# -----
alias h="history"
alias c="clear"
alias hgrep="history | sort -nr -k 1 | sort -u -k 2 | sort -n -k 1 | grep"
alias egrep="egrep --color=auto"
alias mi="micro"
alias vi="vim"
